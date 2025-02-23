let myDate = new Date();
let hrs = myDate.getHours();
let greeting;

if (hrs < 12)
    greeting = 'Good Morning, ';
else if (hrs >= 12 && hrs <= 17)
    greeting = 'Good Afternoon, ';
else if (hrs >= 17 && hrs <= 24)
    greeting = 'Good Evening, ';

document.getElementById('greetings').innerHTML ='<b>' + greeting + '</b>';