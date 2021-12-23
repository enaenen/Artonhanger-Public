package com.artonhanger.manage.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public enum CategoryWebEnum {

    ALL_PRODUCT("All product","s2020081995d55265d2b11"),
    ABSTRACT("추상","s201901085c33eb89b5083"),
    ANIMAL("동물","s20200731a0c1446457b30"),
    CHARACTER("All product","s2020080397e024699f9c7"),
    PERSON("인물","s201901085c33eb8319099"),
    FOOD("음식","s2020080894445da888e32"),
    OBJECT("사물","s2020080880c378c1c36ba"),
    LANDSCAPE("풍경","s2020080803421a5d70f6d"),
    FLOWER("꽃","s201901075c32f8b61a85c"),
    ALL_MATERIALS("All materials","s202008181554dccbc2453"),
    OIL_PASTEL("오일파스텔","s202008185a7108a35a344"),
    COLOR_PENCIL("색연필","s20200819ed51c628f9eb8"),
    OIL_COLOR("유화","s20200818f8b69f100537a"),
    ARCRYLE("아크릴화","s20200818b804f00f6502e"),
    PENCIL("펜화","s202008185a7108a35a344"),
    WATERCOLOR("수채화","s20200818421249dfe30aa"),
    SCULPTURE("도예/입체", "s20210607080122eea907f"),
    SCULPTURE_M("도예/입체", "s20210607b418e17755633"),
    ARTONHANGER_PASS("아트온행거패스","s202102018ae1ab68d8e24");



    private ImwebCategory imwebCategory;
    CategoryWebEnum(String name, String code) {
        this.imwebCategory = new ImwebCategory(name, code);
    }

    public String getCategoryCode() {
        return this.imwebCategory.getCode();
    }

    public String getCategoryName() {
        return this.imwebCategory.getName();
    }

    @Getter
    @Setter
    public class ImwebCategory {
        private String code;
        private String name;

        public ImwebCategory(String name, String code) {
            this.code = code;
            this.name = name;
        }
    }
}