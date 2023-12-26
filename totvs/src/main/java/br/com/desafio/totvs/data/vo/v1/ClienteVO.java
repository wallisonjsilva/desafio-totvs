package br.com.desafio.totvs.data.vo.v1;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Classe ValueObject para Cliente
 * 
 * @author Wallison Junior Cardoso Soares Silva
 */
public class ClienteVO implements Serializable {

	/**
	 * Serial number cliente VO
     * @author              Wallison Junior Cardoso Soares Silva
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Id cliente VO
     * @author              Wallison Junior Cardoso Soares Silva
	 */
	private Long id;
	/**
	 * Nome cliente VO
     * @author              Wallison Junior Cardoso Soares Silva
	 */
	private String nome;
	/**
	 * Sexo cliente VO
     * @author              Wallison Junior Cardoso Soares Silva
	 */
	private String sexo;
	/**
	 * Data nascimento cliente VO
     * @author              Wallison Junior Cardoso Soares Silva
	 */
	private Date dataNascimento;
	/**
	 * Telefones cliente VO
     * @author              Wallison Junior Cardoso Soares Silva
	 */
	private List<TelefoneVO> telefones;

	/**
	 * Método get id cliente
	 * @return	Retorna id do cliente
     * @author              Wallison Junior Cardoso Soares Silva
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Método set id cliente
	 * @param id	Id cliente para ser inserido
     * @author              Wallison Junior Cardoso Soares Silva
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Método get nome cliente
	 * @return	Retorna nome cliente
     * @author              Wallison Junior Cardoso Soares Silva
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * Método set nome cliente
	 * @param nome	Nome cliente para ser inserido
     * @author              Wallison Junior Cardoso Soares Silva
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}

	/**
	 * Método get sexo cliente
	 * @return	Retorna sexo do cliente
     * @author              Wallison Junior Cardoso Soares Silva
	 */
	public String getSexo() {
		return sexo;
	}

	/**
	 * Método set sexo cliente
	 * @param sexo	Sexo cliente para ser inserido
     * @author              Wallison Junior Cardoso Soares Silva
	 */
	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	/**
	 * Método get data nascimento cliente
	 * @return	Retorna data nascimento cliente
     * @author              Wallison Junior Cardoso Soares Silva
	 */
	public Date getDataNascimento() {
		return dataNascimento;
	}

	/**
	 * Método set data nascimento cliente
	 * @param dataNascimento	Data nascimento para ser inserido
     * @author              Wallison Junior Cardoso Soares Silva
	 */
	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	/**
	 * Método get telefones cliente
	 * @return	Retorna telefones do cliente.
     * @author              Wallison Junior Cardoso Soares Silva
	 */
	public List<TelefoneVO> getTelefones() {
		return telefones;
	}

	/**
	 * Método set telefones cliente
	 * @param telefones	Telefones pra ser inserido
     * @author              Wallison Junior Cardoso Soares Silva
	 */
	public void setTelefones(List<TelefoneVO> telefones) {
		this.telefones = telefones;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + ((sexo == null) ? 0 : sexo.hashCode());
		result = prime * result + ((dataNascimento == null) ? 0 : dataNascimento.hashCode());
		result = prime * result + ((telefones == null) ? 0 : telefones.hashCode());
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
		ClienteVO other = (ClienteVO) obj;
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
		if (telefones == null) {
			if (other.telefones != null)
				return false;
		} else if (!telefones.equals(other.telefones))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;

		return true;
	}
}
