package dhbw.datenbanken.ormexample.data.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;


@Entity
@Table(name = "NOTIFICATION")
public class Notification implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "notification_number", nullable = false)
    protected Long notificationNumber;

    @Column(nullable = false)
    @Size(max = 300)
    protected String title;

    @Column
    @Size(max = 1500)
    protected String description;

    @Column
    @Min(2)
    @Max(100)
    protected Integer status;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinTable(name = "PROBLEM_TO_NOTIFICATION",
            joinColumns = {@JoinColumn(name = "notification_number", nullable = false)}, inverseJoinColumns = {@JoinColumn(name = "problem_number", nullable = false)})
    protected Problem problem;


    public Notification() {
        // Default constructor
    }


    public Long getNotificationNumber() {
        return notificationNumber;
    }


    public void setNotificationNumber( Long reportNumber ) {
        this.notificationNumber = reportNumber;
    }


    public String getTitle() {
        return title;
    }


    public void setTitle( String title ) {
        this.title = title;
    }


    public String getDescription() {
        return description;
    }


    public void setDescription( String description ) {
        this.description = description;
    }


    public Integer getStatus() {
        return status;
    }


    public void setStatus( Integer status ) {
        this.status = status;
    }


    public Problem getProblem() {
        return problem;
    }


    public void setProblem( Problem problem ) {
        this.problem = problem;
    }


    @Override
    public boolean equals( Object o ) {
        if ( this == o ) {
            return true;
        }

        if ( o == null || getClass() != o.getClass() ) {
            return false;
        }

        Notification notification = (Notification) o;
        return Objects.equals( notificationNumber, notification.notificationNumber );
    }


    @Override
    public int hashCode() {
        return Objects.hash( notificationNumber, title, description, status );
    }

}
