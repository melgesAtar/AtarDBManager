function getCookie(name) {
            const value = `; ${document.cookie}`;
            const parts = value.split(`; ${name}=`);
            if (parts.length === 2) return parts.pop().split(';').shift();
            return null;
        }

        document.addEventListener('DOMContentLoaded', () => {
            const sessionData = getCookie('sessionCookie');
            let userInfo = {};

            if (sessionData) {
                userInfo = JSON.parse(sessionData);
            }

            if (userInfo.username) {
                document.getElementById('userSpan').innerText = userInfo.username;
            }
        });