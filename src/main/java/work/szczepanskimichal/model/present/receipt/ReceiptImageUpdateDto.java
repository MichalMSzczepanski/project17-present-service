package work.szczepanskimichal.model.present.receipt;

import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Builder(toBuilder = true)
@Getter
public class ReceiptImageUpdateDto {

    private UUID id;
    private MultipartFile image;

}