const API = "http://localhost:8080/api/v1/matriculas"

async function matricularAluno() {

    const aluno = document.getElementById("aluno").value;

    const disciplina = document.getElementById("disciplina").value;

    if(aluno.trim() === "" || disciplina.trim() === "") {

        alert("Preencha todos os campos.");

        return;
    }

    console.log("Nova matrícula:", aluno, disciplina);

    alert("Matrícula realizada com sucesso!");

}