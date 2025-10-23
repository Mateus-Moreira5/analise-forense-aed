package br.edu.icev.aed.forense;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
public class SolucaoForense implements AnaliseForenseAvancada {
    public SolucaoForense(){

    }
    @Override
    public Set<String> encontrarSessoesInvalidas(String caminhoArquivoCsv) throws IOException {
        Set<String> sessoesInvalidas = new HashSet<>();
        Map<String, Stack<String>> pilhaSessoes = new HashMap<>();
        try(BufferedReader br = new BufferedReader(new FileReader(caminhoArquivoCsv))){
            String linha; 
            boolean primeiraLinha = true;
            
            while ((linha = br.readLine()) != null) {
                if(primeiraLinha){
                    primeiraLinha = false;
                    continue;
                }
                String[] campos = linha.split(",");
                if(campos.length < 4) continue;

                String userID = campos[1].trim();
                String sessionID = campos[2].trim();
                String actionType = campos[3].trim();
                if (!pilhaSessoes.containsKey(userID)){
                    pilhaSessoes.put(userID, new Stack<>());
                }
                Stack<String> pilhaUsuario = pilhaSessoes.get(userID);
                if("LOGIN".equals(actionType)){
                    if(!pilhaUsuario.isEmpty()){
                        sessoesInvalidas.add(pilhaUsuario.peek());
                    }
                    pilhaUsuario.push(sessionID);
                } else if("LOGOUT".equals(actionType)){
                    if(pilhaUsuario.isEmpty()){
                        sessoesInvalidas.add(sessionID);
                    } else if (!pilhaUsuario.peek().equals(sessionID)){
                        sessoesInvalidas.add(pilhaUsuario.peek());
                        sessoesInvalidas.add(sessionID);
                    } else {
                        pilhaUsuario.pop();
                    }
                }
            }
        }
        for(Stack<String> pilha : pilhaSessoes.values()){
            sessoesInvalidas.addAll(pilha);
        }
        return sessoesInvalidas;
    }
    @Override
    public List<String> reconstruirLinhaTempo(String arg0, String arg1) throws IOException {
        return null;
    }
    @Override
    public List<Alerta> priorizarAlertas(String arg0, int arg1) throws IOException {
        return null;
    }
    @Override
    public Map<Long, Long> encontrarPicosTransferencia(String arg0) throws IOException {
        return null;
    }
    @Override
    public Optional<List<String>> rastrearContaminacao(String arg0, String arg1, String arg2) throws IOException {
        return Optional.empty();
    }
}
