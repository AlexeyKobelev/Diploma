function getSessionInfo() {
    return fetch('/api/session/info')
        .then(response => response.json())
        .then(data => ({ username: data.username }))
        .catch(error => {
            console.error("Error fetching session info:", error);
            return { username: "Guest" };
        });
}

function loadMessages(chatType, entityId) {
    // Отправляем запрос на сервер для получения истории сообщений
    fetch(`/api/messages?type=${chatType}&id=${entityId}`)
        .then(response => response.json())
        .then(messages => {
            messages.forEach(msg => {
                appendMessage(msg.text, msg.user === username ? 'sent' : 'received');
            });
        })
        .catch(error => {
            console.error("Error loading messages:", error);
        });
}

getSessionInfo().then(({ username }) => {
    const section = document.querySelector('section.section');
    const chatType = section.id;  // chatType (например, teamChat)
    const entityId = section.getAttribute('data-id');  // entityId (например, team ID)

    console.log("Chat type:", chatType);
    console.log("Entity ID:", entityId);

    // Загружаем историю сообщений
    loadMessages(chatType, entityId);

    // Создаем WebSocket подключение
    const socket = new WebSocket(`ws://localhost:8081/ws?type=${chatType}&id=${entityId}`);

    const chatInput = document.querySelector('.chat-input');
    const sendButton = document.querySelector('.send-button');
    const messagesContainer = document.getElementById('chat-messages');

    sendButton.addEventListener('click', sendMessage);
    chatInput.addEventListener('keydown', (e) => {
        if (e.key === 'Enter') sendMessage();
    });

    function sendMessage() {
        const message = chatInput.value.trim();
        if (!message) return;

        const payload = JSON.stringify({ user: username, text: message });
        socket.send(payload);
        chatInput.value = '';
    }

    socket.addEventListener('message', (event) => {
        try {
            const { user, text } = JSON.parse(event.data);
            const type = user === username ? 'sent' : 'received';
            appendMessage(text, type);
        } catch (err) {
            console.error("Ошибка при парсинге:", err);
        }
    });

    function appendMessage(text, type) {
        const messageDiv = document.createElement('div');
        messageDiv.classList.add('chat-message', type);
        messageDiv.textContent = text;
        messagesContainer.appendChild(messageDiv);
        messagesContainer.scrollTop = messagesContainer.scrollHeight;
    }
});
