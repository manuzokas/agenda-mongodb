package com.example.dao;

import com.example.connection.MongoDBConnection;
import com.example.model.Contato;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

/*
 * Classe: ContatoDAO
 * Pacote: com.example.dao
 *
 * Descrição:
 * Esta classe fornece operações de acesso a dados (DAO) para a entidade Contato no banco de dados MongoDB.
 * Ela gerencia operações CRUD básicas (Create, Read, Update, Delete) e consultas específicas para a coleção de contatos.
 * Implementa a interface AutoCloseable para garantir que o cliente MongoDB seja fechado corretamente quando não for mais necessário.
 *
 * Componentes:
 * - MongoClient: Cliente MongoDB usado para gerenciar a conexão com o banco de dados.
 * - MongoDatabase: Instância do banco de dados MongoDB obtida da classe MongoDBConnection.
 * - MongoCollection<Contato>: Coleção tipada de contatos no banco de dados MongoDB.
 *
 * Construtor:
 * - ContatoDAO(): Inicializa a instância do banco de dados e a coleção de contatos utilizando a conexão fornecida pela classe MongoDBConnection.
 *
 * Métodos:
 * - limparBancoDeDados(): Remove o banco de dados inteiro e exibe uma mensagem de confirmação.
 * - adicionarContato(Contato contato): Adiciona um novo contato à coleção e atualiza o ID do contato.
 * - listarContatos(): Retorna uma lista de todos os contatos na coleção.
 * - atualizarContato(String id, Contato contatoAtualizado): Atualiza um contato existente na coleção com base no ID fornecido.
 * - removerContato(String id): Remove um contato da coleção com base no ID fornecido.
 * - listarContatosPorCidade(String cidade): Retorna uma lista de contatos filtrados pela cidade especificada.
 * - listarContatosComMaisDeUmTelefone(): Retorna uma lista de contatos que possuem mais de um número de telefone.
 *
 * Notas:
 * - Certifique-se de que a conexão com o banco de dados seja gerenciada adequadamente para evitar vazamentos de recursos.
 * - Os métodos de consulta utilizam filtros baseados em campos específicos no documento Contato.
 * - O método close() deve ser chamado para liberar recursos do MongoClient quando a instância DAO não for mais necessária.
 */

public class ContatoDAO implements AutoCloseable { 

    private MongoClient mongoClient;
    private MongoDatabase database;
    private MongoCollection<Contato> collection; // definindo minha colecao tipada

    public ContatoDAO() {
        this.database = MongoDBConnection.getDatabase();
        this.collection = MongoDBConnection.getCollection();
    }

    public void limparBancoDeDados() {
        database.drop();
        System.out.println("Banco de dados limpo com sucesso");
    }

    public void adicionarContato(Contato contato) {
        collection.insertOne(contato);
        contato.setId(contato.getId());
    }

    public List<Contato> listarContatos() {
        return collection.find().into(new ArrayList<>());
    }

    public void atualizarContato(String id, Contato contatoAtualizado) {
        collection.replaceOne(Filters.eq("_id", new ObjectId(id)), contatoAtualizado);
    }

    public void removerContato(String id) {
        collection.deleteOne(Filters.eq("_id", new ObjectId(id)));
    }

    public List<Contato> listarContatosPorCidade(String cidade) {
        return collection.find(Filters.eq("endereco.cidade", cidade)).into(new ArrayList<>());
    }

    public List<Contato> listarContatosComMaisDeUmTelefone() {
        return collection.find(Filters.size("telefones", 2)).into(new ArrayList<>());
    }

    @Override
    public void close() {
        if (mongoClient != null) {
            mongoClient.close();
        }
    }
}
