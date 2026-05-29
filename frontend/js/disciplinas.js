const API = "http://localhost:8080/disciplinas";

async function cadastrarDisciplina() {

    const disciplina = document.getElementById("disciplina").value;

    if (disciplina.trim() === "") {

        alert("Digite o nome da disciplina.");

        return;
    }

    console.log("Disciplina cadastrada:", disciplina);

    alert("Disciplina cadastrada com sucesso!");

}