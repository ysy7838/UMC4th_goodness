package umc.precending.domain.school;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class Search {
    private String type;
    private String keyword;

    public Search(String type, String keyword) {
        this.type = type;
        this.keyword = keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getKeyword() {
        return keyword;
    }
}
