package io.github.jhipster.application.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;

import io.github.jhipster.application.domain.enumeration.AnnouncementType;

/**
 * A Announcement.
 */
@Entity
@Table(name = "announcement")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Announcement implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "active")
    private Boolean active;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private AnnouncementType type;

    @Column(name = "reward_info")
    private String rewardInfo;

    @Column(name = "created_date")
    private Instant createdDate;

    @Column(name = "contest_id")
    private Long contestId;

    @ManyToOne
    @JsonIgnoreProperties("announcements")
    private Contest contestId;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public Announcement title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public Announcement description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean isActive() {
        return active;
    }

    public Announcement active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public AnnouncementType getType() {
        return type;
    }

    public Announcement type(AnnouncementType type) {
        this.type = type;
        return this;
    }

    public void setType(AnnouncementType type) {
        this.type = type;
    }

    public String getRewardInfo() {
        return rewardInfo;
    }

    public Announcement rewardInfo(String rewardInfo) {
        this.rewardInfo = rewardInfo;
        return this;
    }

    public void setRewardInfo(String rewardInfo) {
        this.rewardInfo = rewardInfo;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public Announcement createdDate(Instant createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public Long getContestId() {
        return contestId;
    }

    public Announcement contestId(Long contestId) {
        this.contestId = contestId;
        return this;
    }

    public void setContestId(Long contestId) {
        this.contestId = contestId;
    }

    public Contest getContestId() {
        return contestId;
    }

    public Announcement contestId(Contest contest) {
        this.contestId = contest;
        return this;
    }

    public void setContestId(Contest contest) {
        this.contestId = contest;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Announcement)) {
            return false;
        }
        return id != null && id.equals(((Announcement) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Announcement{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", description='" + getDescription() + "'" +
            ", active='" + isActive() + "'" +
            ", type='" + getType() + "'" +
            ", rewardInfo='" + getRewardInfo() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", contestId=" + getContestId() +
            "}";
    }
}
