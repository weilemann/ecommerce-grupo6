package br.org.serratec.backend.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import io.swagger.annotations.ApiModelProperty;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, 
property  = "id", 
scope     = Long.class)
public class Pedido {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_pedido")
	@ApiModelProperty(value= "Identificador único do Pedido", required = true)
	private Long id;
	@ApiModelProperty(value= "Data do Pedido", required = true)
	private LocalDate dataPedido;
	@ApiModelProperty(value= "Data da entrega do Pedido", required = true)
	private LocalDate dataEntrega;
	@ApiModelProperty(value= "Data do envio do Pedido", required = true)
	private LocalDate dataEnvio;
	
	private String status;
	@ManyToOne
	@JoinColumn(name = "id_cliente")
	private Cliente cliente;
	
	@JsonManagedReference("pd")
	@OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<ItemPedido> itemPedidos = new ArrayList<ItemPedido>();

	
	

	public Pedido() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {

		String data = "PEDIDO \nDataEntrega: " + dataEntrega + "\nDataEnvio: " + dataEnvio;
		for (ItemPedido itemPedido : itemPedidos) {
			System.out.println("aqui");
			data += "\nProduto - " + itemPedido.getProduto().getNome() + " - " + itemPedido.getQuantidade() + " - R$" + itemPedido.getPrecoVenda();
		}

		data += "\n\n\nPreço Total: " + getTotal();

		return data;
	}




	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDate getDataPedido() {
		return dataPedido;
	}

	public void setDataPedido(LocalDate dataPedido) {
		this.dataPedido = dataPedido;
	}

	public LocalDate getDataEntrega() {
		return dataEntrega;
	}

	public void setDataEntrega(LocalDate dataEntrega) {
		this.dataEntrega = dataEntrega;
	}

	public LocalDate getDataEnvio() {
		return dataEnvio;
	}

	public void setDataEnvio(LocalDate dataEnvio) {
		this.dataEnvio = dataEnvio;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public List<ItemPedido> getItemPedidos() {
		return itemPedidos;
	}

	public void setItemPedidos(List<ItemPedido> itemPedidos) {
		this.itemPedidos = itemPedidos;
	}

	public Pedido(Long id, LocalDate dataPedido, LocalDate dataEntrega, LocalDate dataEnvio, String status,
			Cliente cliente, List<ItemPedido> itemPedidos) {
		super();
		this.id = id;
		this.dataPedido = dataPedido;
		this.dataEntrega = dataEntrega;
		this.dataEnvio = dataEnvio;
		this.status = status;
		this.cliente = cliente;
		this.itemPedidos = itemPedidos;
	}

	public Double getTotal() {
		double soma = 0.0;
		for (ItemPedido itemPedido : itemPedidos) {
			soma += itemPedido.getSubTotal();

		}
		return soma;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		Pedido other = (Pedido) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
