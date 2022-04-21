package dhbw.datenbanken.ormexample.data.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


@Entity
@Table(name = "PROBLEM")
public class Problem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "problem_number", nullable = false)
    protected Long problemNumber;

    @Column(nullable = false)
    @Size(max = 300)
    protected String title;

    @Column
    @Min(0)
    @Max(100)
    protected Integer status;

    @OneToMany(mappedBy = "problem", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    protected Set<Notification> notifications;


    public Problem() {
        notifications = new HashSet<>();
    }


    public Long getProblemNumber() {
        return problemNumber;
    }


    public void setProblemNumber( Long problemNumber ) {
        this.problemNumber = problemNumber;
    }


    public String getTitle() {
        return title;
    }


    public void setTitle( String title ) {
        this.title = title;
    }


    public Integer getStatus() {
        return status;
    }


    public void setStatus( Integer status ) {
        this.status = status;
    }


    public Set<Notification> getNotifications() {
        return Collections.unmodifiableSet( notifications );
    }


    public void setNotifications( Set<Notification> notifications ) {
        this.notifications = new HashSet<>( notifications );
    }


    public void addNotification( Notification notification ) {
        if ( notification == null ) {
            throw new NullPointerException( "Can't add null Notification" );
        }

        if ( !this.notifications.contains( notification ) ) {
            this.notifications.add( notification );
            notification.setProblem( this );
        }
    }


    public void removeNotification( Notification notification ) {
        if ( notification == null ) {
            throw new NullPointerException( "Can't remove null Notification" );
        }

        if ( this.notifications.contains( notification ) ) {
            this.notifications.remove( notification );
            notification.setProblem( null );
        }
    }


    @Override
    public boolean equals( Object o ) {
        if ( this == o ) {
            return true;
        }

        if ( o == null || getClass() != o.getClass() ) {
            return false;
        }

        Problem problem = (Problem) o;
        return Objects.equals( problemNumber, problem.problemNumber );
    }


    @Override
    public int hashCode() {
        return Objects.hash( problemNumber, title, status );
    }

}
