package org.invoice.service.product;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.invoice.expection.BusinessException;
import org.invoice.model.Product;
import org.invoice.repository.ProductRepository;

import java.util.List;

@ApplicationScoped
public class ProductServiceImpl implements ProductService {

    @Inject
    ProductRepository productRepository;

    @Override
    public List<Product> list(String filter) {
        if (filter == null || filter.isEmpty()) {
            return productRepository.listAll();
        } else {
            return productRepository.list("descricao like ?1", "%" + filter + "%");
        }
    }

    @Override
    public Product getById(Long id) {
        return productRepository.findById(id);
    }

    @Transactional
    @Override
    public Product create(Product product) {
        productRepository.persist(product);
        return product;
    }

    @Override
    @Transactional
    public Product update(Long id, Product produto) {
        Product existente = productRepository.findById(id);
        if (existente == null) {
            throw new BusinessException("Produto não encontrado");
        }

        existente.descricao = produto.descricao;
        existente.situacao = produto.situacao;

        return existente;    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (productRepository.hasMovement(id)) {
            throw new BusinessException("Não é possível excluir produto com movimentação");
        }
        productRepository.deleteById(id);
    }
}