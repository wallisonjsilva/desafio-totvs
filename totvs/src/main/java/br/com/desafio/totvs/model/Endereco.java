package br.com.desafio.totvs.model;

import java.io.Serializable;

import org.hibernate.annotations.CascadeType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * Classe da entidade endereço
 * 
 * @author 	Wallison Junior Cardoso Soares Silva
 */
@Entity
@Table(name = "endereco")
public class Endereco implements Serializable {

  /**
   * Serial numero classe
   */
  private static final long serialVersionUID = 2L;

  /**
   * Id endereço
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  /**
   * Logradouro endereço
   */
  @Column(name = "logradouro", nullable = false, length = 120)
  private String logradouro;

  /**
   * Bairro endereço
   */
  @Column(name = "bairro", nullable = false, length = 50)
  private String bairro;

  /**
   * Cep endereço
   */
  @Column(name = "cep", nullable = false, length = 9)
  private String cep;

  /**
   * Cidade endereço
   */
  @Column(name = "cidade", nullable = false, length = 50)
  private String cidade;

  /**
   * Estado endereço
   */
  @Column(name = "estado", nullable = false, length = 2)
  private String estado;

  /**
   * Pais endereço
   */
  @Column(name = "pais", nullable = false, length = 50)
  private String pais;

  /**
   * Numero endereço
   */
  @Column(name = "numero", nullable = false, length = 10)
  private Integer numero;

  /**
   * Cliente_id endereço relacionamento com cliente
   */
  @ManyToOne()
  @JoinColumn(name = "cliente_id")
  private Cliente cliente;

  /**
   * Método get id endereço
   * @return  Retorna id do endereço
   */
  public Long getId() {
    return id;
  }

  /**
   * Método set id endereço
   * @param id  Id endereço para inserir
   */
  public void setId(Long id) {
    this.id = id;
  }

  /**
   * Método get logradouro endereço
   * @return Retorna logradouro do endereço
   */
  public String getLogradouro() {
    return logradouro;
  }

  /**
   * Método set logradouro endereço
   * @param logradouro Logradouro para inserir
   */
  public void setLogradouro(String logradouro) {
    this.logradouro = logradouro;
  }

  /**
   * Método get bairro endereço
   * @return Retorna bairro do endereço
   */
  public String getBairro() {
    return bairro;
  }

  /**
   * Método set bairro endereço
   * @param bairro  Bairro para inserir no endereço
   */
  public void setBairro(String bairro) {
    this.bairro = bairro;
  }

  /**
   * Método get cep endereço
   * @return  Retorna cep do endereço
   */
  public String getCep() {
    return cep;
  }

  /**
   * Método set cep endereço
   * @param cep Cep pra inserir
   */
  public void setCep(String cep) {
    this.cep = cep;
  }

  /**
   * Método get cidade endereço
   * @return  Retorna cidade do endereço
   */
  public String getCidade() {
    return cidade;
  }

  /**
   * Método set cidade endereço
   * @param cidade  Cidade inserir no endereço
   */
  public void setCidade(String cidade) {
    this.cidade = cidade;
  }

  /**
   * Método get estado endereço
   * @return Retorna estado endereço
   */
  public String getEstado() {
    return estado;
  }

  /**
   * Método set estado endereço
   * @param estado  Estado inserir no endereço
   */
  public void setEstado(String estado) {
    this.estado = estado;
  }

  /**
   * Método get pais endereço
   * @return  Retorna pais do endereço
   */
  public String getPais() {
    return pais;
  }

  /**
   * Método set pais endereço
   * @param pais  Pais para inserir no endereço
   */
  public void setPais(String pais) {
    this.pais = pais;
  }

  /**
   * Método get numero endereço
   * @return  Retorna numero do endereço
   */
  public int getNumero() {
    return numero;
  }

  /**
   * Método set numero endereço
   * @param numero Numero para inserir no endereço
   */
  public void setNumero(int numero) {
    this.numero = numero;
  }

  /**
   * Método get cliente endereço
   * @return  Retorna cliente do endereço
   */
  public Cliente getCliente() {
    return cliente;
  }

  /**
   * Método set cliente endereço
   * @param cliente Cliente para inserir no endereço
   */
  public void setCliente(Cliente cliente) {
    this.cliente = cliente;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    result = prime * result + ((logradouro == null) ? 0 : logradouro.hashCode());
    result = prime * result + ((bairro == null) ? 0 : bairro.hashCode());
    result = prime * result + ((cep == null) ? 0 : cep.hashCode());
    result = prime * result + ((cidade == null) ? 0 : cidade.hashCode());
    result = prime * result + ((estado == null) ? 0 : estado.hashCode());
    result = prime * result + ((pais == null) ? 0 : pais.hashCode());
    result = prime * result + ((numero == null) ? 0 : numero.hashCode());
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
    Endereco other = (Endereco) obj;
    if (logradouro == null) {
      if (other.logradouro != null)
        return false;
    } else if (!logradouro.equals(other.logradouro))
      return false;
    if (bairro == null) {
      if (other.bairro != null)
        return false;
    } else if (!bairro.equals(other.bairro))
      return false;
    if (cep == null) {
      if (other.cep != null)
        return false;
    } else if (!cep.equals(other.cep))
      return false;
    if (id == null) {
      if (other.id != null)
        return false;
    } else if (!id.equals(other.id))
      return false;
    if (cidade == null) {
      if (other.cidade != null)
        return false;
    } else if (!cidade.equals(other.cidade))
      return false;
    if (estado == null) {
      if (other.estado != null)
        return false;
    } else if (!estado.equals(other.estado))
      return false;
    if (pais == null) {
      if (other.pais != null)
        return false;
    } else if (!pais.equals(other.pais))
      return false;
    if (numero == null) {
      if (other.numero != null)
        return false;
    } else if (!numero.equals(other.numero))
      return false;
    if (cliente == null) {
      if (other.cliente != null)
        return false;
    } else if (!cliente.equals(other.cliente))
      return false;

    return true;
  }
}
