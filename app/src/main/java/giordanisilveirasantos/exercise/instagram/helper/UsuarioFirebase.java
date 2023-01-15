package giordanisilveirasantos.exercise.instagram.helper;

import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import giordanisilveirasantos.exercise.instagram.model.Usuario;

public class UsuarioFirebase {

    public static FirebaseUser getUsuarioAtual(){

        FirebaseAuth usuario = ConfiguracaoFirebase.getFirebaseAutenticao();
        return usuario.getCurrentUser();

    }

    public static String getIdentificadorUsuario(){
        return getUsuarioAtual().getUid();
    }

    public static void atualizarNomeUsuario(String nome){
        try {
            //usuario logado no app
            FirebaseUser usuarioLogado = getUsuarioAtual();

            //configurar objeto para alteração do perfil
            UserProfileChangeRequest profile = new UserProfileChangeRequest.Builder().setDisplayName(nome).build();
            usuarioLogado.updateProfile(profile).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(!task.isSuccessful()){
                        Log.d("Perfil", "Erro ao atualizar o nome de perfil!");
                    }
                }
            });

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void atualizarFotoUsuario(Uri url){
        try {
            //usuario logado no app
            FirebaseUser usuarioLogado = getUsuarioAtual();

            //configurar objeto para alteração do perfil
            UserProfileChangeRequest profile = new UserProfileChangeRequest.Builder().setPhotoUri(url).build();
            usuarioLogado.updateProfile(profile).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(!task.isSuccessful()){
                        Log.d("Perfil", "Erro ao atualizar a foto de perfil!");
                    }
                }
            });

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static Usuario getDadosUsuarioLogado(){
        FirebaseUser firebaseUser = getUsuarioAtual();

        Usuario usuario = new Usuario();
        usuario.setEmail(firebaseUser.getEmail());
        usuario.setNome(firebaseUser.getDisplayName());
        usuario.setId(firebaseUser.getUid());

        if(firebaseUser.getPhotoUrl() == null){
            usuario.setCaminhoFoto("");
        }else{
            usuario.setCaminhoFoto(firebaseUser.getPhotoUrl().toString());
        }
        return usuario;
    }
}
