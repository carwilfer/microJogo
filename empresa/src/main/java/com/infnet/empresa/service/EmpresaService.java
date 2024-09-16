package com.infnet.empresa.service;

//import com.infnet.empresa.configRabbit.RabbitConfig;
import com.infnet.empresa.dto.EmpresaDTO;
import com.infnet.empresa.model.Empresa;
import com.infnet.empresa.repository.EmpresaRepository;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmpresaService {

    private final EmpresaRepository empresaRepository;
    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public EmpresaService(EmpresaRepository empresaRepository, RabbitTemplate rabbitTemplate) {
        this.empresaRepository = empresaRepository;
        this.rabbitTemplate = rabbitTemplate;
    }

    public EmpresaDTO criarEmpresa(EmpresaDTO empresaDTO) {
        Empresa empresa = new Empresa();
        empresa.setUsuarioId(empresaDTO.getId());
        BeanUtils.copyProperties(empresaDTO, empresa, "id");
        Empresa empresaSalva = empresaRepository.save(empresa);
        return new EmpresaDTO(empresaSalva);
    }

    public Optional<EmpresaDTO> encontrarPorId(Long id) {
        return empresaRepository.findByUsuarioId(id).map(EmpresaDTO::new);
    }

//    public EmpresaDTO encontrarPorId(Long id) {
//        Optional<Empresa> empresa = empresaRepository.findById(id);
//        if (empresa.isPresent()) {
//            return new EmpresaDTO(empresa.get());
//        } else {
//            throw new RuntimeException("Empresa não encontrada");
//        }
//    }

    public Optional<EmpresaDTO> encontrarPorCnpj(String cnpj) {
        String cnpjNormalizado = cnpj.replace("[^0-9]", "");
        return empresaRepository.findByCnpj(cnpj)
                .map(EmpresaDTO::new);
    }

    public List<EmpresaDTO> listarTodas() {
        List<Empresa> empresas = empresaRepository.findAll();
        return empresas.stream()
                .map(EmpresaDTO::new)
                .toList();
    }

    public void deletarEmpresa(Long id) {
        empresaRepository.deleteById(id);
    }

    public void ativarEmpresa(Long id) {
        Empresa empresa = empresaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Empresa não encontrada"));
        empresa.setAtivo(true);
        empresaRepository.save(empresa);
    }

    public void desativarEmpresa(Long id) {
        Empresa empresa = empresaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Empresa não encontrada"));
        empresa.setAtivo(false);
        empresaRepository.save(empresa);
    }

    private EmpresaDTO convertToDTO(Empresa empresa) {
        return new EmpresaDTO(empresa);
    }
}

