package br.com.desafio.totvs.model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * Classe da entidade telefone
 * 
 * @author Wallison Junior Cardoso Soares Silva
 */
@Entity
@Table(name = "telefone")
public class Telefone implements Serializable {

    /**
     * Serial number telefone
     */
    private static final long serialVersionUID = 1L;

    /**
     * Id telefone
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * contato telefone
     */
    @Column(name = "contato", nullable = false, length = 20)
    private String contato;

    /**
     * cliente telefone
     */
    @ManyToOne(optional = false)
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    /**
     * Método get id telefone
     * 
     * @return Retorna id telefone
     */
    public Long getId() {
        return id;
    }

    /**
     * Método set id telefone
     * 
     * @param id Id telefone para inserir
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Método get contato telefone
     * 
     * @return Retorna contato do cliente
     */
    public String getContato() {
        return contato;
    }

    /**
     * Método set contato telefone
     * 
     * @param contato Contato para inserir
     */
    public void setContato(String contato) {
        this.contato = contato;
    }

    /**
     * Método get cliente telefone
     * 
     * @return Retorna cliente do telefone
     */
    public Cliente getCliente() {
        return cliente;
    }

    /**
     * Método set cliente telefone
     * 
     * @param cliente Cliente do telefone para inserir
     */
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((contato == null) ? 0 : contato.hashCode());
        result = prime * result + ((cliente == null) ? 0 : cliente.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Telefone other = (Telefone) obj;
        if (contato == null) {
            if (other.contato != null)
                return false;
        } else if (!contato.equals(other.contato))
            return false;
        if (cliente == null) {
            if (other.cliente != null)
                return false;
        } else if (!cliente.equals(other.cliente))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;

        return true;
    }
}
