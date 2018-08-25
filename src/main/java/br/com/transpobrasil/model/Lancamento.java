package br.com.transpobrasil.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "Lancamento")
@NamedQueries({
        @NamedQuery(name = "Lancamento.findByTop10MaxValueItemAndDescStartA",
                query = "SELECT la FROM Lancamento la WHERE EXISTS ( SELECT 1 FROM la.items i WHERE SUBSTRING(i.description, 1, 1) = 'A' " +
                        "GROUP BY i HAVING SUM(i.value) > 50) ORDER BY la "),
        @NamedQuery(name = "Lancamento.findByCountItemsGreaterThan10",
                query = "SELECT new br.com.transpobrasil.model.Lancamento(la.id, la.dateInitial, la.dateFinal, la.valueTotal, CONCAT(la.observation,' - Possuem mais que 10 itens')) " +
                        "FROM Lancamento la JOIN la.items i GROUP BY la HAVING COUNT(i) > 10")
})
public class Lancamento {


    public Lancamento(Long id, Date dateInitial, Date dateFinal, Double valueTotal, String observation) {
        this.id = id;
        this.dateInitial = dateInitial;
        this.dateFinal = dateFinal;
        this.valueTotal = valueTotal;
        this.observation = observation;
    }

    public Lancamento() {
    }

    @Id
    @SequenceGenerator(name = "pk_oid_lancamento", sequenceName = "lancamento_seq", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "lancamento_seq")
    @Column(name = "oid", nullable = false, updatable = false)
    private Long id;

    @Column(name = "dt_inicial")
    private Date dateInitial;

    @Column(name = "dt_final")
    private Date dateFinal;

    @Column(name = "vl_total", length = 8, precision = 2)
    private Double valueTotal;

    @Column(name = "observacao", length = 1000)
    private String observation;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "LancamentoItem",
            joinColumns = @JoinColumn(name = "oid_lancamento"),
            inverseJoinColumns = @JoinColumn(name = "oid_item"))
    private Set<Item> items;

    public Long getId() {
        return id;
    }

    protected void setId(Long id) {
        this.id = id;
    }

    public Date getDateInitial() {
        return dateInitial;
    }

    public void setDateInitial(Date dateInitial) {
        this.dateInitial = dateInitial;
    }

    public Date getDateFinal() {
        return dateFinal;
    }

    public void setDateFinal(Date dateFinal) {
        this.dateFinal = dateFinal;
    }

    public Double getValueTotal() {
        return valueTotal;
    }

    public void setValueTotal(Double valueTotal) {
        this.valueTotal = valueTotal;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public Set<Item> getItems() {
        return items;
    }

    public void setItems(Set<Item> items) {
        this.items = items;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Lancamento)) {
            return false;
        }

        Lancamento that = (Lancamento) o;

        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Lancamento{");
        sb.append("id=").append(id);
        sb.append(", dateInitial=").append(dateInitial);
        sb.append(", dateFinal=").append(dateFinal);
        sb.append(", valueTotal=").append(valueTotal);
        sb.append(", observation='").append(observation).append('\'');
        sb.append('}');
        return sb.toString();
    }

}
