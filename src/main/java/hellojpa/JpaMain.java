package hellojpa;

import javax.persistence.*;
import java.util.List;

public class JpaMain {
    public static void main(String[] args) {
        // 어플리케이션이 뜰 때, 하나만 생성된다.
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa-demo-application");

        // 데이터 베이스 커넥션을 하나 받는다고 생각할 수 있다.
        // 요청 마다 새로 생성하여 엔티티를 관리한다.
        // 쓰레드 세이프하지 않아서 공유를 절대하지 않고 쓰고 버려야한다.
        EntityManager em = emf.createEntityManager();

        // jpa 는 무조건 트랜잭션 안에서 사용해야한다.
        EntityTransaction transaction = em.getTransaction();

        // 트랜잭션 시작.
        transaction.begin();

        try {
            // jpql 을 통해 직접 쿼리를 작성할 수 있다.
            // jpql 은 엔티티에 쿼리를 날리는 개념이다. (객체 지향 SQL)
            List<Member> result = em.createQuery("select m from Member m", Member.class)
                    .getResultList();

            for (Member m : result) {
                System.out.println("Member name: "+m.getName());
            }
            // 트랜잭션 종료
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        } finally {
            em.close();
        }


        // 자원 회수
        em.close();
        emf.close();
    }
}
