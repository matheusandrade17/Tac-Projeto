const API = "http://localhost:8080/api/v1/avaliacoes"

async function registrarAvaliacao() {

    const aluno = document.getElementById("aluno").value;

    const nota = document.getElementById("nota").value;

    if(aluno.trim() === "" || nota.trim() === "") {

        alert("Preencha todos os campos.");

        return;
    }

    console.log("Avaliação registrada:", aluno, nota);

    alert("Avaliação registrada com sucesso!");

}