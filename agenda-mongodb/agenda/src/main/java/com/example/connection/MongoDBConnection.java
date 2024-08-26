package com.example.connection;

import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.pojo.PojoCodecProvider;

import com.example.model.Contato;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

/*
 * Classe: MongoDBConnection
 * Pacote: com.example.connection
 *
 * Descrição:
 * Esta classe fornece uma conexão gerenciada com o banco de dados MongoDB, utilizando a biblioteca oficial do MongoDB para Java.
 * Ela configura o cliente MongoDB e o banco de dados específico ("agenda_db") com um codec personalizado para a classe Contato.
 * A conexão é estabelecida na inicialização estática da classe e verifica se o MongoDB está acessível com um comando de ping.
 * Caso a conexão seja bem-sucedida, uma mensagem é exibida; caso contrário, um erro é reportado.
 *
 * Componentes:
 * - MongoClient: Cliente MongoDB que gerencia a conexão com o banco de dados.
 * - MongoDatabase: Instância do banco de dados ("agenda_db") que é utilizada para acessar coleções.
 *
 * Métodos:
 * - getDatabase(): Retorna a instância do banco de dados MongoDB.
 * - getCollection(): Retorna a coleção "contatos" configurada para trabalhar com objetos da classe Contato.
 *
 * Dependências:
 * - org.bson.codecs.configuration.CodecProvider
 * - org.bson.codecs.configuration.CodecRegistries
 * - org.bson.codecs.pojo.PojoCodecProvider
 * - com.mongodb.MongoClientSettings
 * - com.mongodb.MongoException
 * - com.mongodb.client.MongoClient
 * - com.mongodb.client.MongoClients
 * - com.mongodb.client.MongoCollection
 * - com.mongodb.client.MongoDatabase
 *
 * Notas:
 * - O codec para a classe Contato é registrado para garantir que a serialização e desserialização ocorram corretamente.
 * - A conexão com o banco de dados é estabelecida de forma estática e é verificada através de um comando de ping.
 * - Certifique-se de que o banco de dados "agenda_db" e a coleção "contatos" existam para evitar exceções durante a execução.
 */

public class MongoDBConnection {
    private static MongoClient mongoClient;
    private static MongoDatabase database;

    static {
        CodecProvider pojoCodecProvider = PojoCodecProvider.builder().automatic(true).build();
        var settings = MongoClientSettings.builder()
                .codecRegistry(CodecRegistries.fromRegistries(
                        MongoClientSettings.getDefaultCodecRegistry(),
                        CodecRegistries.fromProviders(pojoCodecProvider)))
                .build();

        mongoClient = MongoClients.create(settings);
        database = mongoClient.getDatabase("agenda_db");

        try {
            mongoClient.getDatabase("admin").runCommand(new org.bson.Document("ping", 1));
            System.out.println("Conexão estabelecida com sucesso");
        } catch (MongoException e) {
            System.err.println("Erro ao conectar ao MongoDB: " + e.getMessage());
        }
    }

    public static MongoDatabase getDatabase() {
        return database;
    }

    public static MongoCollection<Contato> getCollection() {
        return database.getCollection("contatos", Contato.class);
    }
}
