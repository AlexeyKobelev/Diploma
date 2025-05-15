document.addEventListener('DOMContentLoaded', async function () {
    const teamId = new URLSearchParams(window.location.search).get('id');

    if (!teamId) {
        console.error('ID команды не найден в URL');
        return;
    }

    try {
        const response = await fetch(`/api/team/${teamId}`);

        if (response.status === 403) {
            window.location.href = "/access-team-denied"; // замени на нужный URL
            return;
        }

        if (!response.ok) throw new Error("Ошибка загрузки команды");

        const team = await response.json();

        // Обновляем название команды
        const teamNameSpan = document.getElementById('team-name-text');
        teamNameSpan.textContent = team.team_name;

        const editButton = document.getElementById('edit-team-name-btn');
        attachEditButtonListener();

        function attachEditButtonListener() {
            editButton.addEventListener('click', function () {
                const input = document.createElement('input');
                input.type = 'text';
                input.value = teamNameSpan.textContent;
                input.classList.add('team-name-input');

                teamNameSpan.replaceWith(input);
                input.focus();

                input.addEventListener('blur', () => finishEditing(input));
                input.addEventListener('keydown', (e) => {
                    if (e.key === 'Enter') {
                        finishEditing(input);
                    }
                });

                function finishEditing(inputEl) {
                    const newTeamName = inputEl.value.trim();
                    if (newTeamName === '') {
                        alert('Название команды не может быть пустым.');
                        inputEl.focus();
                        return;
                    }

                    saveTeamDescription(teamId, newTeamName, descriptionTextarea.value, team.emails);

                    const newSpan = document.createElement('span');
                    newSpan.id = 'team-name-text';
                    newSpan.classList.add('team-name');
                    newSpan.textContent = newTeamName;
                    inputEl.replaceWith(newSpan);

                    attachEditButtonListener();
                }
            });
        }

        const descriptionTextarea = document.querySelector('.team-description-container');
        if (descriptionTextarea) {
            descriptionTextarea.value = team.description || '';
            descriptionTextarea.addEventListener('blur', () => saveTeamDescription(teamId, teamNameSpan.textContent, descriptionTextarea.value, team.emails));
        }

        const participantsContainer = document.querySelector('.team-participants');
        const countContainer = document.querySelector('.participants-count');
        if (participantsContainer && countContainer) {
            participantsContainer.innerHTML = '';
            const users = team.emails || [];

            if (users.length === 0) {
                participantsContainer.innerHTML = '<p>Нет участников</p>';
            } else {
                users.forEach(user => {
                    const userLink = document.createElement('a');
                    userLink.href = `/profile?id=${user.id_user}`;
                    userLink.classList.add('participant-item');
                    userLink.textContent = user.email;
                    participantsContainer.appendChild(userLink);
                });
            }

            countContainer.textContent = `Участников: ${users.length}`;
        }

        const photoContainer = document.getElementById('photo-container');
        const teamImageInput = document.getElementById('teamImage');

        function renderUploadButton() {
            photoContainer.innerHTML = `
                <button id="uploadTrigger" class="upload-btn"><img src="/icons/load-photo.svg"></button>
            `;
            document.getElementById('uploadTrigger').addEventListener('click', () => {
                teamImageInput.click();
            });
        }

        function renderTeamImage(imageUrl) {
            photoContainer.innerHTML = '';
            const img = document.createElement('img');
            img.src = imageUrl;
            img.alt = "Фото команды";
            img.classList.add('team-photo');
            photoContainer.appendChild(img);

            // 🆕 Клик по фото для повторной загрузки
            img.addEventListener('click', () => {
                teamImageInput.click();
            });
        }

        if (team.teamImgPath != null && team.teamImgPath.trim() !== '') {
            renderTeamImage(team.teamImgPath);
        } else {
            renderUploadButton();
        }

        teamImageInput.addEventListener('change', function () {
            const file = this.files[0];
            if (!file || !teamId) return;

            const formData = new FormData();
            formData.append('image', file);
            formData.append('teamId', teamId);

            fetch('/api/team/upload-image', {
                method: 'POST',
                body: formData
            })
                .then(res => {
                    if (!res.ok) throw new Error('Ошибка загрузки изображения');
                    return res.json();
                })
                .then(data => {
                    renderTeamImage(data.imageUrl);
                })
                .catch(err => console.error('Ошибка загрузки изображения:', err));
        });

        // 🆕 Добавление участников через модалку
        const addMembersBtn = document.querySelector('.add-members-btn');
        const modal = document.getElementById('add-members-modal');
        const confirmBtn = document.getElementById('confirm-add-members');
        const cancelBtn = document.getElementById('cancel-add-members');
        const membersInput = document.getElementById('members-input');

        addMembersBtn.addEventListener('click', () => {
            modal.style.display = 'flex';
            membersInput.value = '';
        });

        cancelBtn.addEventListener('click', () => {
            modal.style.display = 'none';
        });

        confirmBtn.addEventListener('click', async () => {
            const emails = membersInput.value
                .split(',')
                .map(email => email.trim())
                .filter(email => email.length > 0);

            if (emails.length === 0) {
                alert('Введите хотя бы одну почту!');
                return;
            }

            try {
                const response = await fetch(`/api/team/${teamId}/add-members`, {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify(emails)
                });

                if (!response.ok) {
                    throw new Error('Ошибка при добавлении участников');
                }
                modal.style.display = 'none';
                location.reload();
            } catch (error) {
                console.error('Ошибка добавления участников:', error);
                alert('Не удалось добавить участников');
            }
        });

    } catch (err) {
        console.error("Ошибка при загрузке команды:", err);
    }
   // 🆕 Дополнительные действия: Покинуть или удалить команду
    const moreOptionsBtn = document.querySelector('.more-options-btn');
    const moreOptionsMenu = document.querySelector('.more-options-menu');

    moreOptionsBtn.addEventListener('click', (e) => {

        moreOptionsMenu.style.display = moreOptionsMenu.style.display === 'none' ? 'block' : 'none';
    });

// Закрытие меню при клике вне
    document.addEventListener('click', (e) => {
        if (!moreOptionsBtn.contains(e.target) && !moreOptionsMenu.contains(e.target)) {
            moreOptionsMenu.style.display = 'none';
        }
    });

// Работа с модалкой
    const modal = document.getElementById('more-options-modal');
    const confirmationMessage = modal.querySelector('.more-options-modal-message');
    const confirmBtn = modal.querySelector('.more-options-modal-btn.confirm');
    const cancelBtn = modal.querySelector('.more-options-modal-btn.cancel');

    function openModal(message, onConfirm) {
        confirmationMessage.textContent = message;
        modal.classList.remove('hidden');
        modal.classList.add('active');

        const confirmHandler = () => {
            closeModal();
            onConfirm();
        };

        confirmBtn.addEventListener('click', confirmHandler, { once: true });
        cancelBtn.addEventListener('click', closeModal, { once: true });

        function closeModal() {
            modal.classList.remove('active');
            modal.classList.add('hidden');
        }
    }

// Покинуть команду
    document.addEventListener('click', (e) => {
        if (e.target.closest('.option-leave')) {
            moreOptionsMenu.style.display = 'none';
            openModal('Вы уверены, что хотите покинуть команду? Все связи с командой пропадут!', async () => {
                try {
                    const response = await fetch(`/api/team/${teamId}/leave`, {
                        method: 'POST'
                    });
                    if (!response.ok) throw new Error('Ошибка при выходе из команды');
                    window.location.href = '/teams';
                } catch (error) {
                    console.error('Ошибка при выходе из команды:', error);
                    alert('Не удалось покинуть команду');
                }
            });
        }
    });

// Удалить команду
    document.addEventListener('click', (e) => {
        if (e.target.closest('.option-delete')) {
            moreOptionsMenu.style.display = 'none';
            openModal('Вы уверены, что хотите удалить команду? Это действие необратимо.', async () => {
                try {
                    const response = await fetch(`/api/team/${teamId}/delete`, {
                        method: 'DELETE'
                    });
                    if (!response.ok) throw new Error('Ошибка при удалении команды');
                    window.location.href = '/teams';
                } catch (error) {
                    console.error('Ошибка при удалении команды:', error);
                    alert('Не удалось удалить команду');
                }
            });
        }
    });

});

// Функция сохранения описания команды и имени
function saveTeamDescription(teamId, teamName, description, emailsArray) {
    const payload = {
        team_name: teamName.trim(),
        description: description,
        emails: emailsArray.map(user => user.email)
    };

    fetch(`/api/editteam/${teamId}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(payload)
    })
        .then(response => {
            if (!response.ok) throw new Error('Ошибка при обновлении');
            console.log('Данные команды успешно обновлены');
        })
        .catch(error => {
            console.error('Ошибка при сохранении команды:', error);
        });


}
