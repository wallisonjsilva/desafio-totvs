package br.com.desafio.totvs.utils;

/**
 * Classe responsável por validação de valores nulo
 * 
 * @author  Wallison Junior Cardoso Soares Silva
 */
public class ValidarNulo {

    /**
     * Método responsável por verificar se valor é nulo
     * 
     * @param <T>           Tipo da entidade que estar sendo validada
     * @param valor         Valor atual do objeto
     * @param valorPadrao   Valor anterior do objeto
     * @return              Retorna Valor Padrão se for nulo ou Novo Valor caso contrário
     * @author              Wallison Junior Cardoso Soares Silva
     */
    public <T> T naoNulo(T valor, T valorPadrao) {
        return valor != null ? valor : valorPadrao;
    }
}
