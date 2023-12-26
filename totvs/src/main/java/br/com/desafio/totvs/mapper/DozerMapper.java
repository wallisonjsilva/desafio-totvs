package br.com.desafio.totvs.mapper;

import java.util.ArrayList;
import java.util.List;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;


/**
 * Classe responsável por Realizar o mapeamento do Objeto
 * recebido no service para entidade do repository.
 * 
 * @author      Wallison Junior Cardoso Soares Silva
 */
public class DozerMapper {
    
    /**
     * Builder padrão do mapper do dozer
     */
    private static Mapper mapper = DozerBeanMapperBuilder.buildDefault();

    /**
     * Método que realizar a conversão de objeto simples entre uma Entidade e um VO
     * @param <O>           Representa um objeto genérico da classe de origem
     * @param <D>           Representa um objeto genérico da classe de destino
     * @param origin        Objeto VO/Entity para conversão
     * @param destination   Objeto VO/Entity alvo para conversão
     * @return              Retorna o Objetivo convertido para classe alvo
     * @author              Wallison Junior Cardoso Soares Silva
     */
    public static<O, D> D parseObject(O origin, Class<D> destination) {
        return mapper.map(origin, destination);
    }

    /**
     * Método que realizar a conversão de uma lista de objetos entre uma Entidade e um VO
     * @param <O>           Representa um objeto genérico da classe de origem
     * @param <D>           Representa um objeto genérico da classe de destino
     * @param origin        Objeto VO/Entity para conversão
     * @param destination   Objeto VO/Entity alvo para conversão
     * @return              Retorna o Objetivo convertido para classe alvo
     * @author              Wallison Junior Cardoso Soares Silva
     */
    public static<O, D> List<D> parseListObject(List<O> origin, Class<D> destination) {
        List<D> destinationObjects = new ArrayList<D>();
        for (O o : origin) {
            destinationObjects.add(mapper.map(o, destination));
        }
        return destinationObjects;
    }
}
