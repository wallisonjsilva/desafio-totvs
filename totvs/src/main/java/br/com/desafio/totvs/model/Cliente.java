package br.com.desafio.totvs.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

/**
 * Classe da entidade cliente
 * 
 * @author Wallison Junior Cardoso Soares Silva
 */
@Entity
@Table(name = "cliente")
public class Cliente implements Serializable {

	/**
	 * Serial number
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Id do cliente
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/**
	 * Nome do cliente
	 */
	@Column(name = "nome", nullable = false, length = 100)
	private String nome;

	/**
	 * Sexo do cliente
	 */
	@Column(name = "sexo", nullable = false, length = 1)
	private String sexo;

	/**
	 * Data nascimento do cliente
	 */
	@Column(name = "data_nascimento", nullable = false)
	private Date dataNascimento;

	/*
	 * @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval =
	 * true)
	 * private List<Telefone> telefones = new ArrayList<Telefone>();
	 */

	/**
	 * Método get Id do cliente
	 * 
	 * @return Retorna id do cliente
	 * @author Wallison Junior Cardoso Soares Silva
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Método set Id do cliente
	 * 
	 * @param id Id do cliente que deve ser inserido
	 * @author Wallison Junior Cardoso Soares Silva
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Método get nome do cliente
	 * 
	 * @return Retornar nome do cliente
	 * @author Wallison Junior Cardoso Soares Silva
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * Método set nome cliente
	 * 
	 * @param nome Nome do cliente que deve ser inserido
	 * @author Wallison Junior Cardoso Soares Silva
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}

	/**
	 * Método get sexo cliente
	 * 
	 * @return Retorna sexo do cliente
	 * @author Wallison Junior Cardoso Soares Silva
	 */
	public String getSexo() {
		return sexo;
	}

	/**
	 * Método set sexo cliente
	 * 
	 * @param sexo Sexo do cliente que deve ser inserido
	 * @author Wallison Junior Cardoso Soares Silva
	 */
	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	/**
	 * Método get data nascimento
	 * 
	 * @return Retorna data nascimento do cliente
	 * @author Wallison Junior Cardoso Soares Silva
	 */
	public Date getDataNascimento() {
		return dataNascimento;
	}

	/**
	 * Método set data nascimento cliente
	 * 
	 * @param dataNascimento Data nascimento do cliente que deve ser inserido
	 * @author Wallison Junior Cardoso Soares Silva
	 */
	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	/*
	 * public List<Telefone> getTelefones() {
	 * return telefones;
	 * }
	 * 
	 * public void setTelefones(List<Telefone> telefones) {
	 * this.telefones = telefones;
	 * }
	 */

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + ((sexo == null) ? 0 : sexo.hashCode());
		result = prime * result + ((dataNascimento == null) ? 0 : dataNascimento.hashCode());
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
		Cliente other = (Cliente) obj;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (sexo == null) {
			if (other.sexo != null)
				return false;
		} else if (!sexo.equals(other.sexo))
			return false;
		if (dataNascimento == null) {
			if (other.dataNascimento != null)
				return false;
		} else if (!dataNascimento.equals(other.dataNascimento))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;

		return true;
	}
}
