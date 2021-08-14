package com.example.demo;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import lombok.AllArgsConstructor;

@AllArgsConstructor

@Controller
@RequestMapping("/clientes")
public class LeeStoreController {
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@GetMapping
	public ModelAndView listar(@ModelAttribute("clienteEmEdicao") Cliente clienteEmEdicao) {
		List<Cliente> clientes = this.clienteRepository.findAll();
		ModelAndView modelAndView = new ModelAndView("listar");
		modelAndView.addObject("clientes", clientes);
		return modelAndView; 
	}
	
	@GetMapping("/antesAlterar")
	public ModelAndView antesAlterar(@RequestParam("id") Long id) {
		Optional<Cliente> clienteOpcional = this.clienteRepository.findById(id);
		if(clienteOpcional.isPresent()) {
			List<Cliente> clientes = this.clienteRepository.findAll();
			ModelAndView modelAndView = new ModelAndView("listar");
			modelAndView.addObject("clientes", clientes);
			modelAndView.addObject("clienteEmEdicao", clienteOpcional.get());
			return modelAndView; 
		}else {
			return new ModelAndView("naoEncontrado");
		}
	}
	
	@PostMapping("/alterar")
	public ModelAndView alterar(Cliente cliente) {
		this.clienteRepository.save(cliente);
		return new ModelAndView("sucessoAlteracao");
	}
	
	@PostMapping("/inserir")
	public ModelAndView inserir(Cliente cliente) {
		this.clienteRepository.save(cliente);
		return new ModelAndView("sucessoInsercao");
	}
	
	@GetMapping("/excluir")
	public ModelAndView excluir(@RequestParam("id") Long id) {
		System.out.println(id);
		this.clienteRepository.deleteById(id);
		return new ModelAndView("sucessoExclusao");
	}
}
