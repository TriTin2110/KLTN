package vn.kltn.KLTN.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;

@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Entity
public class News extends Component {
	private String authorName, shortDescription;
	private String content;

	public News() {
	}

	public News(String name, String image, String authorName, String content, String shortDescription) {
		super(name, image);
		this.authorName = authorName;
		this.content = content;
		this.shortDescription = shortDescription;
	}

	public String getAuthorName() {
		return authorName;
	}

	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}

	public String getShortDescription() {
		return shortDescription;
	}

	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
