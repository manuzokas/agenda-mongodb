package com.example.model;

import java.util.List;

import org.bson.types.ObjectId;

public class Contato {
    private ObjectId id;
    private String nome;
    private List<String> telefones;
    private Endereco endereco;

    public Contato() {
    }

    public Contato(String nome, List<String> telefones, Endereco endereco) {
        this.nome = nome;
        this.telefones = telefones;
        this.endereco = endereco;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<String> getTelefones() {
        return telefones;
    }

    public void setTelefones(List<String> telefones) {
        this.telefones = telefones;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    @Override
    public String toString() {
        return String.format(
                "Contato:\n" +
                        "    ID: %s\n" +
                        "    Nome: %s\n" +
                        "    Telefones: %s\n" +
                        "    Endereço:\n%s",
                id, nome, telefones, endereco);
    }

}
