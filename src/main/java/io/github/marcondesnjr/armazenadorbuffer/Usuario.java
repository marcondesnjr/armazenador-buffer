package io.github.marcondesnjr.armazenadorbuffer;

/**
 *
 * @author José Marcondes do Nascimento Junior
 */
public class Usuario implements Ilike{

    private String email;
    private  String nome;

    public Usuario(String email, String nome) {
        this.email = email;
        this.nome = nome;
    }

    
    
    @Override
    public boolean ilike(Object elem) {
        if(elem instanceof Usuario)
            return ((Usuario) elem).getEmail().equals(this.email);
        return false;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    
    
}
