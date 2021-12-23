package com.artonhanger.manage.model;

import com.artonhanger.manage.enums.*;
import com.artonhanger.manage.exception.AOHException;
import com.artonhanger.manage.model.dto.ArtworkModifyDto;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.util.StringUtils.hasText;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"title", "price", "soldOut"})
@EqualsAndHashCode
public class Artwork {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "artwork_frame_id")
    private ArtworkFrame artworkFrame;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "artwork")
    private List<OrderedArtworkList> orderedArtworkLists = new ArrayList<>();

    @OneToMany(mappedBy = "artwork", cascade = CascadeType.ALL)
    @Fetch(FetchMode.SUBSELECT)
    private List<ArtworkImage> artworkImages = new ArrayList<>();

    private String thumbnail;

    @OneToMany(mappedBy = "artwork", cascade = CascadeType.ALL)
    private List<Category> categories = new ArrayList<>();

    @OneToMany(mappedBy = "artwork", cascade = CascadeType.ALL)
    private List<Material> materials = new ArrayList<>();

    @OneToMany(mappedBy = "artwork", cascade = CascadeType.ALL)
    private List<Color> colors = new ArrayList<>();


    @OneToMany(mappedBy = "artwork")
    private List<ContentArtworkPivot> contentArtworkPivots = new ArrayList<>();

    @OneToMany(mappedBy = "artwork", cascade = CascadeType.ALL)
    @Fetch(FetchMode.SUBSELECT)
    private List<Bookmark> bookmarks = new ArrayList<>();

    private String title;
    private int price;
    private int deliveryPrice;
    private String productionYear;

    @Embedded
    private ArtworkSize size;

    @Column(name = "size")
    private int sizeNumber;
    private boolean soldOut = false;
    private Long imwebId;

    @Enumerated(EnumType.STRING)
    private ShippingEnum shipping;
    private Boolean photoService;

    @Enumerated(EnumType.STRING)
    private DeliveryStateEnum deliveryState = DeliveryStateEnum.ONSALE;

    @Embedded
    private DbMetaProperty meta = new DbMetaProperty();

    public void soldout() {
        this.soldOut = true;
        this.deliveryState = DeliveryStateEnum.PAIED;
    }

    public void cancled() {
        this.soldOut = false;
        this.deliveryState = DeliveryStateEnum.ONSALE;
        this.meta.setEnable(true);
    }

    @Builder
    public Artwork(Long id, String title, int price, int deliveryPrice, String productionYear,
                   double height, double width, Member member, List<ArtworkImage> artworkImages, String thumbnail, int sizeNumber,
                   List<CategoryEnum> categories, List<MaterialEnum> materials, List<ColorEnum> colors,
                   ShippingEnum shipping, Boolean photoService, ArtworkFrame artworkFrame) {
        this.id = id;
        this.thumbnail = thumbnail;
        this.sizeNumber = sizeNumber;
        this.title = title;
        this.price = price;
        this.deliveryPrice = deliveryPrice;
        this.productionYear = productionYear;
        this.size = ArtworkSize.builder().height(height).width(width).build();
        this.member = member;
        //TODO 21-01-25 NULL시, 처리를 해야함
        if (artworkImages != null) {
            this.artworkImages = artworkImages.stream().map(artworkImageDTO ->
                            new ArtworkImage(artworkImageDTO.getName(), artworkImageDTO.getDescription()))
                    .peek(x -> x.setArtwork(this))
                    .collect(Collectors.toList());
        }
        if (categories != null) {
            this.categories = categories.stream().map(category ->
                            new Category(this, category))
                    .collect(Collectors.toList());
        }
        if (materials != null) {
            this.materials = materials.stream().map(material ->
                            new Material(this, material))
                    .collect(Collectors.toList());
        }
        if (colors != null) {
            this.colors = colors.stream().map(color ->
                            new Color(this, color))
                    .collect(Collectors.toList());
        }
        this.shipping = shipping;
        this.photoService = photoService;
        this.soldOut = false;
        this.artworkFrame = artworkFrame;
        this.meta = new DbMetaProperty();
    }

    public void changeTitle(String title) {
        if (StringUtils.hasText(title))
            this.title = title;
    }

    public void changeProductionYear(String productionYear) {
        if (StringUtils.hasText(productionYear))
            this.productionYear = productionYear;
    }

    public void changeSize(double height, double width) {
        if (height * width <= 0)
            return;
        this.size.setHeight(height);
        this.size.setWidth(width);
    }

    public void changeHeight(double height) {
        if (height <= 0)
            return;
        this.size.setHeight(height);
    }

    public void changeWidth(double width) {
        if (width <= 0)
            return;
        this.size.setWidth(width);
    }

    public void modifySize(ArtworkModifyDto artworkModifyDto) {
        artworkModifyDto.verifySize();
        double height = artworkModifyDto.getHeight();
        double width = artworkModifyDto.getWidth();
        if (height == 0 && width != 0)
            changeWidth(width);
        if (height != 0 && width == 0)
            changeHeight(height);
        else
            changeSize(height, width);
    }

    public void changeCategories(List<Category> categories) {
        if (categories == null || categories.size() <= 0)
            return;
        this.categories = categories;
    }

    public void changeMaterials(List<Material> materials) {
        if (materials == null || materials.size() <= 0)
            return;
        this.materials = materials;
    }

    public void changeArtworkFrame(ArtworkFrame artworkFrame) {
        if (artworkFrame == null)
            return;
        this.artworkFrame = artworkFrame;
    }

    public void deleteArtworkFrame(){
        this.artworkFrame = null;
    }

    // TODO [hkpark] 비즈니스 로직이 복잡해서 테스트 작성이 필요해 보임
    /**
     *
     * @param artworkFrame (액자 테이블)
     * @param artworkModifyDto (artwork 수정정보)
     */
    public void modifyArtworkFrame(ArtworkFrame artworkFrame, ArtworkModifyDto artworkModifyDto) {
        //artworkFrame(액자)에대한 변경사항이 없거나 null일경우
        if (artworkModifyDto.getPhotoFrame()==null || !artworkModifyDto.getPhotoFrame())
            return;
        //현재 Artwork가 액자가 없었던 상태인데, artworkFrame(액자)을 있다는 상태로 변경할 경우
        if (artworkFrame == null && artworkModifyDto.getPhotoFrame()) {
            this.artworkFrame = ArtworkFrame.builder().height(artworkModifyDto.getFrameHeight())
                    .width(artworkModifyDto.getFrameWidth())
                    .material(artworkModifyDto.getPhotoFrameMaterial())
                    .build();
            return;
        }
        if (artworkFrame == null || "NO".equals(artworkModifyDto.getPhotoFrame())) {
            this.artworkFrame = null;
            return ;
        }
        artworkModifyDto.verifyFrameSize();
        artworkFrame.changeHeight(artworkModifyDto.getFrameHeight());
        artworkFrame.changeWidth(artworkModifyDto.getFrameWidth());
        artworkFrame.changeMaterial(artworkModifyDto.getPhotoFrameMaterial());
    }

    private void changeDeliveryPrice(String deliveryPrice) {
        if (deliveryPrice == null || deliveryPrice.equals(""))
            return;
        this.deliveryPrice = Integer.parseInt(deliveryPrice);
    }

    public void setImwebId(String imwebId) {
        if (!hasText(imwebId))
            throw new AOHException(ErrorEnum.IMWEB_UPLOAD_ERROR);
        this.imwebId = Long.parseLong(imwebId);
    }

    private void changePrice(Integer price) {
        if (price == null || price <= 0)
            return;
        this.price = price;
    }

    public void modifyArtworkByMember(ArtworkModifyDto artworkModifyDto) {
        changeTitle(artworkModifyDto.getTitle());
        changePrice(artworkModifyDto.getPrice());
        changeProductionYear(artworkModifyDto.getProductionYear());
        modifySize(artworkModifyDto);
        changeCategories(artworkModifyDto.getCategories());
        changeMaterials(artworkModifyDto.getMaterials());
        changeDeliveryPrice(artworkModifyDto.getDeliveryPrice());
        changeDescriptions(artworkModifyDto.getDescriptions());
        modifyArtworkFrame(this.getArtworkFrame(), artworkModifyDto);
    }

    private void changeDescriptions(List<String> descriptions) {
        if (descriptions == null || descriptions.size() <= 0)
            return;
        //TODO for문 개선 필요
        //여기서 -1 은 프론트에서 넘어올때 description 한줄당 리스트 한개로 인식하지 않게 하기위해 임의로 description 리스트를 하나 추가해주기 때문이다.
        for (int i = 0; i < descriptions.size() - 1; i++) {
            String description = descriptions.get(i);
            ArtworkImage artworkImage = artworkImages.get(i);
            if (artworkImage == null)
                return;
            if (description == null || description.equals(""))
                continue;
            artworkImage.setDescription(description);
        }
    }

}
