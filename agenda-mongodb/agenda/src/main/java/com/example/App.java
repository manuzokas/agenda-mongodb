package com.example;

import java.util.ArrayList;
import java.util.List;

import com.example.dao.ContatoDAO;
import com.example.model.Contato;
import com.example.model.Endereco;

/* TESTANDO CONEXAO COM MONGO NO CMD:
   cd "C:\Program Files\MongoDB\Server\7.0\bin"
    mongod
 */

public class App {
    public static void main(String[] args) {
        try (ContatoDAO contatoDAO = new ContatoDAO()) {
            contatoDAO.limparBancoDeDados();
            // criando e adicionando contatos
            List<String> telefones1 = new ArrayList<>();
            telefones1.add("123456789");
            telefones1.add("987654321");
            Endereco endereco1 = new Endereco("Cidade1", "Rua1", "Bairro1", "123", "Apto 1");
            Contato contato1 = new Contato("Contato1", telefones1, endereco1);
            contatoDAO.adicionarContato(contato1);

            List<String> telefones2 = new ArrayList<>();
            telefones2.add("111111111");
            Endereco endereco2 = new Endereco("Cidade2", "Rua2", "Bairro2", "456", "Apto 2");
            Contato contato2 = new Contato("Contato2", telefones2, endereco2);
            contatoDAO.adicionarContato(contato2);

            // listando todos os contatos
            System.out.println("Listando todos os contatos:");
            List<Contato> contatos = contatoDAO.listarContatos();
            contatos.forEach(System.out::println);

            // atualizando um contato
            if (!contatos.isEmpty()) {
                Contato primeiroContato = contatos.get(0);
                primeiroContato.setNome("Contato1 Atualizado");
                contatoDAO.atualizarContato(primeiroContato.getId().toString(), primeiroContato);
                System.out.println("Contato atualizado: " + primeiroContato);
            }

            // listando contatos por cidade
            System.out.println("Listando contatos por cidade 'Cidade1':");
            List<Contato> contatosPorCidade = contatoDAO.listarContatosPorCidade("Cidade1");
            contatosPorCidade.forEach(System.out::println);

            // listando contatos com mais de um telefone
            System.out.println("Listando contatos com mais de um telefone:");
            List<Contato> contatosComMaisDeUmTelefone = contatoDAO.listarContatosComMaisDeUmTelefone();
            contatosComMaisDeUmTelefone.forEach(System.out::println);

            // removendo um contato
            if (!contatos.isEmpty()) {
                Contato primeiroContato = contatos.get(0);
                contatoDAO.removerContato(primeiroContato.getId().toString());
                System.out.println("Contato removido: " + primeiroContato);
            }

            // listando todos os contatos novamente após a remoção
            System.out.println("Listando todos os contatos após remoção:");
            contatos = contatoDAO.listarContatos();
            contatos.forEach(System.out::println);
        }
    }
}
