package br.com.alura.alurabank.dominio;

import br.com.alura.alurabank.controller.exceptions.SaldoInsuficienteException;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@NamedEntityGraphs({
        @NamedEntityGraph(
          name = "pegaTudo",
          includeAllAttributes = true
        ),

        @NamedEntityGraph(
                name = "contaComCorrentista",
                attributeNodes = {
                        @NamedAttributeNode("correntista"),
                }

        )
})

@EqualsAndHashCode(of = {"dadosDaConta"})
public class ContaCorrente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Getter
    @Setter
    @Embedded
    private DadosDaConta dadosDaConta;

    @OneToOne(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private Correntista correntista;

    public ContaCorrente(String banco, String agencia, String numero, Correntista correntista){
        this.dadosDaConta = new DadosDaConta(banco, agencia, numero);
        this.correntista = correntista;
    }

    protected ContaCorrente() {
    }

    public boolean identificadaPor(String banco, String agencia, String numero) {
        return this.dadosDaConta.equals(new DadosDaConta(banco, agencia, numero));
    }

    public Integer getId() {
        return id;
    }

    public Correntista getCorrentista() {
        return correntista;
    }

    public DadosDaConta getDadosDaConta() {
        return dadosDaConta;
    }
}
