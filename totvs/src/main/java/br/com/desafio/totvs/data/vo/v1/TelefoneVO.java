package br.com.desafio.totvs.data.vo.v1;

import java.io.Serializable;

/**
 * Classe ValueObject para Telefone
 * 
 * @author Wallison Junior Cardoso Soares Silva
 */
public class TelefoneVO implements Serializable {
    /**
     * Serial number telefone VO
     */
    private static final long serialVersionUID = 1L;

    /**
     * Id telefone
     */
    private Long id;
    /**
     * contato telefone
     */
    private String contato;
    /**
     * cliente telefone
     */
    private ClienteVO cliente;

    /**
     * Método get id telefone
     * @return  Retorna id telefone
     */
    public Long getId() {
        return id;
    }

    /**
     * Método set id telefone
     * @param id Id telefone para inserir
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Método get contato telefone
     * @return  Retorna contato do cliente
     */
    public String getContato() {
        return contato;
    }

    /**
     * Método set contato telefone
     * @param contato Contato para inserir
     */
    public void setContato(String contato) {
        this.contato = contato;
    }

    /**
     * Método get cliente telefone
     * @return  Retorna cliente do telefone
     */
    public ClienteVO getCliente() {
        return cliente;
    }

    /**
     * Método set cliente telefone
     * @param cliente   Cliente do telefone para inserir
     */
    public void setCliente(ClienteVO cliente) {
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
        TelefoneVO other = (TelefoneVO) obj;
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
