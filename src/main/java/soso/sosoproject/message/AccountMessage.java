package soso.sosoproject.message;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AccountMessage {
    String message = "";
    String href = "";

    public AccountMessage(String message, String href) {
        this.message = message;
        this.href = href;
    }
}