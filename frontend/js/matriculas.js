const API = "http://localhost:8080/api/v1/matriculas"

async function listarMatriculas() {
    try {
        const response = await fetch(API);
        const matriculas = await response.json();
        const lista = document.getElementById("listaMatriculas");
        lista.innerHTML = "";
        if (!matriculas.length) {
            lista.innerHTML = '<li class="list-group-item">Nenhuma matrícula encontrada</li>';
            return;
        }
        matriculas.forEach(m => {
            lista.innerHTML += `<li class="list-group-item">${m.nomeAluno} — ${m.nomeDisciplina}</li>`;
        });
    } catch (e) {
        console.error(e);
    }
}

async function matricularAluno() {
    const aluno = document.getElementById("aluno").value;
    const disciplina = document.getElementById("disciplina").value;
    if (aluno.trim() === "" || disciplina.trim() === "") { alert("Preencha todos os campos."); return; }
    try {
        await fetch(API, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ nomeAluno: aluno, nomeDisciplina: disciplina })
        });
        document.getElementById("aluno").value = "";
        document.getElementById("disciplina").value = "";
        listarMatriculas();
    } catch (e) {
        alert("Erro ao realizar matrícula.");
    }
}

listarMatriculas();