package dhbw.datenbanken.ormexample.data.repository;

import dhbw.datenbanken.ormexample.data.model.Problem;


public class ProblemRepository extends GenericRepository<Problem, Long> {

    public ProblemRepository() {
        super( Problem.class );
    }

}
