const API = "http://localhost:8080/api/v1/alunos"

async function listarAlunos() {

    try {

        const response = await fetch(API);

        const alunos = await response.json();

        const lista = document.getElementById("lista");

        lista.innerHTML = "";

        alunos.forEach(aluno => {

            lista.innerHTML += `
                <li class="list-group-item d-flex justify-content-between align-items-center">

                    ${aluno.nome}

                    <span class="badge bg-primary">
                        ID: ${aluno.id}
                    </span>

                </li>
            `;
        });

    } catch (erro) {

        console.error(erro);

        alert("Erro ao carregar alunos.");

    }

}

async function cadastrarAluno() {

    try {

        const nome = document.getElementById("nome").value;

        if(nome.trim() === "") {

            alert("Digite um nome.");

            return;
        }

        await fetch(API, {

            method: "POST",

            headers: {
                "Content-Type": "application/json"
            },

            body: JSON.stringify({
                nome: nome
            })

        });

        document.getElementById("nome").value = "";

        listarAlunos();

    } catch (erro) {

        console.error(erro);

        alert("Erro ao cadastrar aluno.");

    }

}

listarAlunos();