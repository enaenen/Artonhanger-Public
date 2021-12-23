package com.artonhanger.manage.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorEnum {
    /* JWT 오류 메세지 정의 */
    JWT_UNKNOWN_ERROR(HttpStatus.BAD_REQUEST, "JWT_000", "알 수 없는 JWT 오류입니다."),
    JWT_MALFORMED_TOKEN(HttpStatus.BAD_REQUEST, "JWT_001", "잘못된 JWT 토큰입니다."),
    JWT_UNSUPPORTED(HttpStatus.BAD_REQUEST, "JWT_002", "미지원 JWT 토큰 방식입니다."),
    JWT_EXPIRED(HttpStatus.BAD_REQUEST, "JWT_003", "만료된 토큰입니다."),
    /* JWT 오류 메세지 정의 끝 */

    /* 작품 오류 메세지 정의 */
    ARTWORK_NOT_FOUND(HttpStatus.NOT_FOUND, "ARW_000", "해당 작품을 찾을 수 없습니다."),
    ARTWORK_NOT_ORDER(HttpStatus.NOT_FOUND, "ARW_001", "해당 작품의 주문을 찾을 수 없습니다."),
    ARTWORK_IS_SOLD_OUT(HttpStatus.BAD_REQUEST, "ARW_002", "해당 작품은 품절입니다.") ,
    /* 작품 오류 메세지 정의 끝 */

    /* 작가 오류 메세지 정의 */
    ARTIST_NOT_FOUND(HttpStatus.NOT_FOUND, "ART_000", "해당 작가를 찾을 수 없습니다."),
    /* 작가 오류 메세지 정의 */

    /* 결제 오류 메시지 정의 */
    PAYMENT_PRICE_NOT_CORRECT(HttpStatus.BAD_REQUEST, "PMT_000", "가격 검증에 실패하였습니다."),
    PAYMENT_PRICE_NOT_CORRECT_AFFTER(HttpStatus.BAD_REQUEST, "PMT_001", "가격 검증에 실패하였습니다. 위변조 위험이 감지 되었습니다."),
    PAYMENT_DB_SAVE_ERROR(HttpStatus.BAD_REQUEST, "PMT_002", "데이터베이스에 주문 정보를 저장하지 못했습니다. 문의가 필요합니다."),
    PAYMENT_ARTWORK_ALREADY_SOLD_OUT(HttpStatus.BAD_REQUEST, "PMT_003", "이미 팔린 작품이 존재합니다."),
    PAYMENT_UNDIFINED_MEMBER(HttpStatus.BAD_REQUEST, "PMT_004", "결제를 시도한 멤버가 존재하지 않습니다."),
    PAYMENT_RECEIVER_NAME_EMPTY(HttpStatus.BAD_REQUEST, "PMT_005", "수령인 정보가 존재하지 않습니다."),
    PAYMENT_CARD_INFO_NOT_FOUND(HttpStatus.NOT_FOUND, "PMT_006", "카드 정보가 존재하지 않습니다."),
    PAYMENT_IAMPORT_ERROR(HttpStatus.BAD_REQUEST,"PMT_007","결제 오류입니다."),
    /* 결제 오류 메시지 정의 끝*/

    /* 카드 정보 오류 메시지 정의*/
    UNCERTIFIED_CARD(HttpStatus.BAD_REQUEST,"CRD_N_000", "인증불가카드"),
    CARD_NUMBER_ERROR(HttpStatus.BAD_REQUEST,"CRD_N_001", "카드번호 오류"),
    CREDIT_CARD_COMPANY_CALL_REQUEST(HttpStatus.BAD_REQUEST,"CRD_N_002", "카드사전화요망"),
    INVALID_CARD_NUMBER(HttpStatus.BAD_REQUEST,"CRD_N_003", "유효하지않은 카드번호를 입력하셨습니다."),
    NON_USE(HttpStatus.BAD_REQUEST,"CRD_N_004", "사용불가 카드"),
    VALIDITY_PERIOD_ERROR(HttpStatus.BAD_REQUEST,"CRD_E_000", "유효기간 오류"),
    VALIDITY_PERIOD_INCORRECT(HttpStatus.BAD_REQUEST,"CRD_E_001", "유효기간틀림 년월 확인요망"),
    EXPIRATION_CARD_FOR_VALIDITY(HttpStatus.BAD_REQUEST,"CRD_E_002", "유효기간 경과카드"),
    CARD_DUPLICATED(HttpStatus.BAD_REQUEST,"CRD_E_003","이미 등록된 카드입니다."),
    CARD_USER_NOT_MATCH(HttpStatus.BAD_REQUEST,"CRD_E_004","본인 소유의 카드가 아닙니다."),
    RESIDENT_OR_BUSINESS_REGISTRATION_NUMBER_ERROR(HttpStatus.BAD_REQUEST,"CRD_B_000", "주민OR사업자등록번호오류"),
    CARD_PASSWORD_NOT_ENTERED(HttpStatus.BAD_REQUEST,"CRD_P_000", "카드비밀번호 미입력"),
    PASSWORD_ERROR_INPUT(HttpStatus.BAD_REQUEST,"CRD_P_001", "비밀번호오류입력"),
    PASSWORD_INCORRECT(HttpStatus.BAD_REQUEST,"CRD_P_002", "비밀번호틀림"),
    CARD_NUMBER_NULL(HttpStatus.BAD_REQUEST,"CRD_N_004","카드번호 없음"),

    /* 카드 정보 오류 메시지 정의 끝*/

    /* 아트배송 오류 메시지 정의 끝*/
    ARTDELIVERY_SCHEDULED_FAILED(HttpStatus.BAD_REQUEST, "ADS_000", "실패 또는 취소된 구독 결제 입니다."),
    ARTDELIVERY_SCHEDULED_UNKNOWN_ERROR(HttpStatus.BAD_REQUEST, "ADS_001", "알수 없는 status 값입니다."),
    ARTDELIVERY_ARLEADY_SUBSCRBIBE(HttpStatus.BAD_REQUEST, "ADS_002", "이미 구독중 입니다."),
    ARTDELIVERY_ARLEADY_CANCLEDED_SUBSCRBIBE(HttpStatus.BAD_REQUEST, "ADS_003", "이미 구독이 취소되었습니다."),
    IS_NOT_ARTDELIVERY_ARTIST(HttpStatus.BAD_REQUEST, "ADV_000", "아트배송을 구독하지 않은 작가입니다."),
    /* 아트배송 오류 메시지 정의 끝*/

    /*파일 업로드 오류 메세지 정의*/
    FILE_UPLOAD_MAX_FILE_REQUEST_OVER(HttpStatus.BAD_REQUEST, "FUL_000", "허용된 파일 개수를 초과 하였습니다."),
    FILE_UPLOAD_MAX_FILE_SIZE_OVER(HttpStatus.BAD_REQUEST, "FUL_001", "허용된 파일 용량을 초과 하였습니다."),
    FILE_UPLOAD_REQUEST_MAX_FILE_SIZE_OVER(HttpStatus.BAD_REQUEST, "FUL_002", "허용된 파일 총 요청 용량을 초과 하였습니다."),
    FILE_UPLOAD_REQUEST_NULL(HttpStatus.BAD_REQUEST, "FUL_003", "업로드된 사진이 없습니다."),
    /*파일 업로드 오류 메세지 정의 끝*/

    /*주문 오류 메세지 정의*/
    ORDER_IS_NOT_EXIST(HttpStatus.BAD_REQUEST, "ORD_000", "주문 내역이 존재하지 않습니다."),
    /*주문 오류 메세지 정의 끝*/

    /*북마크 오류 메시지 정의*/
    ALREADY_BOOKMAKRED(HttpStatus.BAD_REQUEST, "BKM_000", "이미 북마크한 작품 입니다."),
    EVER_BOOKMAKRED(HttpStatus.BAD_REQUEST, "BKM_001", "북마크가 되어 있지 않습니다."),
    /*북마크 오류 메시지 정의 끝*/
    
    /* 인증 오류 메세지 정의 */
    AUTH_ACCESS_DENIED(HttpStatus.UNAUTHORIZED, "ATH_001", "접근 권한이 없습니다."),
    /* 인증 오류 메세지 정의 끝 */

    /* 카카오톡 인증 오류 메세지 정의 */
    AUTH_KAKAO_UNKNOWN_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "KKO_000", "알 수 없는 카카오 통신 모듈 오류입니다."),
    AUTH_KAKAO_NOT_SUPPORTED_METHOD(HttpStatus.BAD_REQUEST, "KKO_001", "지원하지 않는 인증 방식 입니다."),
    AUTH_KAKAO_NOT_REGISTERED_USER(HttpStatus.NOT_FOUND, "KKO_002", "등록되지 않은 카카오 유저입니다."),
    AUTH_KAKAO_NOT_ACTIVATED(HttpStatus.BAD_REQUEST, "KKO_003", "탈퇴처리 중인 카카오 계정입니다."),
    /* 카카오톡 인증 오류 메세지 정의 끝 */

    /* 멤버 CRUD 오류 메세지 정의 */
    MEMBER_UNKNOWN_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "MBS_000", "알 수 없는 사용자 관리 서비스 오류입니다."),
    MEMBER_DUPLICATED_PROPS(HttpStatus.BAD_REQUEST, "MBS_001", "중복된 속성입니다."),
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "MBS_002", "해당 UID 의 유저를 찾을 수 없습니다."),
    MEMBER_PASSWORD_NOT_CORRECT(HttpStatus.BAD_REQUEST, "MBS_003", "비밀번호가 일치하지 않습니다."),
    MEMBER_INPUT_NOT_FOUND(HttpStatus.BAD_REQUEST, "MBS_004", " 잘못된 입력입니다."),
    MEMBER_IMAGE_DELETE_UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "MID_001", "삭제할 수 없는 이미지입니다."),
    MEMBER_FORMAT_ERROR(HttpStatus.BAD_REQUEST, "MBS_005", "잘못된 형식의 입력입니다."),
    MEMBER_DUPLICATED_ID(HttpStatus.CONFLICT,"MBS_006","중복된 ID 값입니다."),
    BANNED_MEMBER(HttpStatus.FORBIDDEN, "MBS_007", "정지된 유저 입니다."),
    MEMBER_INPUT_OVERLIMIT(HttpStatus.BAD_REQUEST, "MBS_008", "최대 입력을 초과하였습니다."),
    /* 멤버 CRUD 오류 메세지 정의 끝*/


    /* 메일 서버 에러 메세지 정의 */
    MAIL_AUTHENTICATION_ERROR(HttpStatus.INTERNAL_SERVER_ERROR,"MAE_001"," 메일 서버의 인증에 실패했습니다."),
    MAIL_SEND_ERROR(HttpStatus.INTERNAL_SERVER_ERROR,"MAE_002"," 메일 전송에 실패하였습니다."),
    MAIL_DUPLICATED_PROPS(HttpStatus.CONFLICT,"MAE_003","중복된 메일 입니다."),
    MAIL_CODE_NOT_CORRECT(HttpStatus.BAD_REQUEST, "MAE_004", "코드가 일치하지 않습니다."),
    /* 메일 서버 에러 메세지 정의 끝 */

    /* 검색 에러 메세지 정의 끝 */
    SEARCH_UNSUPPORTED_CHARSET(HttpStatus.BAD_REQUEST, "SIE_001", "지원하지 않는 Charset 입니다."),
    SEARCH_NOT_FOUND(HttpStatus.NOT_FOUND, "SIE_002", "검색 결과가 존재하지 않습니다."),
    /* 검색  에러 메세지 정의 끝 */
  
    /*택배 오류 메세지 정의*/
    DELIVERY_NOT_FOUND(HttpStatus.NOT_FOUND, "DLV_000","해당 운송장을 찾을 수 없습니다."),
    DELIVERY_BAD_INPUT(HttpStatus.BAD_REQUEST,"DLV_001","잘못된 운송장 입력입니다."),
    DELIVERY_BAD_COMPANY_INPUT(HttpStatus.BAD_REQUEST,"DLV_002","잘못된 배송회사 입력입니다."),
    /* 택배 오류 메세지 정의 끝 */

    /* 권한 오류 메세지 정의 */
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "AUT_000", "인증 되지 않은 유저입니다."),
    FORBIDDEN(HttpStatus.FORBIDDEN, "AUT_001", "접근 권한이 존재하지 않습니다."),
    /* 권한 오류 메세지 정의 끝 */
    /* 아임웹 오류 */
    IMWEB_UPLOAD_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "IMW_000", "아임웹 업로드에 실패하였습니다."),
    IMWEB_DELETE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "IMW_000", "아임웹 삭제에 실패하였습니다."),
    IMWEB_404_ERROR(HttpStatus.NOT_FOUND, "IMW_002", "웹에 상품이 존재하지 않습니다. 카카오톡 채널로 문의하여주십시오."),

    ETC(HttpStatus.INTERNAL_SERVER_ERROR, "ETC_000", "알 수 없는 서버 내부 오류입니다.");


    private ErrorResponse errorResponse;

    public String getMessage() {
        return this.errorResponse.getMessage();
    }

    public String getErrCode() {
        return this.errorResponse.getErrCode();
    }

    public HttpStatus getHttpStatus() {
        return this.errorResponse.getHttpStatus();
    }

    public static ErrorEnum valueOfMessage(String message){
        for(ErrorEnum errorEnum : ErrorEnum.values()){
            if(message.contains(errorEnum.getMessage()))
                return errorEnum;
        }
        return ETC;
    }

    ErrorEnum(HttpStatus httpStatus, String errCode, String message) {
        this.errorResponse = new ErrorResponse(httpStatus, errCode, message);
    }

    @Getter
    @Setter
    public class ErrorResponse {
        private HttpStatus httpStatus;
        private String errCode;
        private String message;

        public ErrorResponse(HttpStatus httpStatus, String errCode, String message) {
            this.httpStatus = httpStatus;
            this.errCode = errCode;
            this.message = message;
        }
    }

}
