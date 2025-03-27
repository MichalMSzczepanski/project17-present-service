package work.szczepanskimichal.service.s3;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum FileType {

    RECEIPT("receipt/"),
    PRESENT_PURCHASED("presentPurchased/"),
    SCREENSHOT("screenshot/");

    private final String name;

}
