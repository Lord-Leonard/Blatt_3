package dhbw.datenbanken.ormexample.data.model;

import javax.persistence.*;

@Entity
@Table(name = "MEASURE")
public class Measure {
    @Id
    @Column(name = "measure_number", nullable = false)
    private Long id;

    @Column(name = "title", nullable = false, length = 300)
    private String title;

    @Column(name = "description", length = 1500)
    private String description;

    @Column(name = "status", nullable = false)
    private Integer status;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "problem_number", nullable = false)
    private Problem problemNumber;

    public Problem getProblemNumber() {
        return problemNumber;
    }

    public void setProblemNumber(Problem problemNumber) {
        this.problemNumber = problemNumber;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}