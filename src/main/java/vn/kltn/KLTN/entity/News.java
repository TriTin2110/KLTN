package vn.kltn.KLTN.entity;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;

@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Entity
public class News extends Component {
	private String authorName, contentEncrypted;
	@ManyToMany
	@JoinTable(name = "tag_news", joinColumns = @JoinColumn(name = "news_id"), inverseJoinColumns = @JoinColumn(name = "tag_id"))
	private List<Tag> tags;

	public News() {
	}

	public News(String name, String image, String authorName, String contentEncrypted, List<Tag> tags) {
		super(name, image);
		this.authorName = authorName;
		this.contentEncrypted = contentEncrypted;
		this.tags = tags;
	}

	public String getAuthorName() {
		return authorName;
	}

	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}

	public String getContentEncrypted() {
		return contentEncrypted;
	}

	public void setContentEncrypted(String contentEncrypted) {
		this.contentEncrypted = contentEncrypted;
	}

	public List<Tag> getTags() {
		return tags;
	}

	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}

}
