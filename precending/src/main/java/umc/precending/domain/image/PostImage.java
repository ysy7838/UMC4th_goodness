package umc.precending.domain.image;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.web.multipart.MultipartFile;
import umc.precending.domain.post.Post;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class PostImage extends Image {

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Post post;

    public PostImage(MultipartFile file) {
        this.originalName = file.getOriginalFilename();
        this.storedName = generateStoreName(originalName);
        this.accessUrl = "";
    }

    public PostImage(String originalName, String accessUrl) {
        this.originalName = originalName;
        this.storedName = generateStoreName(originalName);
        this.accessUrl = accessUrl;
    }

    public void initImage(Post post) {
        if(this.post == null) this.post = post;
    }
}