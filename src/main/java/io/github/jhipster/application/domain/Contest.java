package io.github.jhipster.application.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Contest.
 */
@Entity
@Table(name = "contest")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Contest implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "contestId")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Announcement> ids = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Announcement> getIds() {
        return ids;
    }

    public Contest ids(Set<Announcement> announcements) {
        this.ids = announcements;
        return this;
    }

    public Contest addId(Announcement announcement) {
        this.ids.add(announcement);
        announcement.setContestId(this);
        return this;
    }

    public Contest removeId(Announcement announcement) {
        this.ids.remove(announcement);
        announcement.setContestId(null);
        return this;
    }

    public void setIds(Set<Announcement> announcements) {
        this.ids = announcements;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Contest)) {
            return false;
        }
        return id != null && id.equals(((Contest) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Contest{" +
            "id=" + getId() +
            "}";
    }
}
