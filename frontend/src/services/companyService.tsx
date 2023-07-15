
import React, {useEffect, useState} from "react";
import "../Style/CreatingCompany.css";
import Cookies from 'js-cookie';


const [redirect, setRedirect] = useState<boolean>(false)
const token = Cookies.get('name');



export function fetchCreateCompany(url, companyData, schedule, employeeEmails) {
    fetch(url, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token} `
        },
        body: JSON.stringify({
            company: companyData,
            emails: {
                emails: employeeEmails
            },
            days: schedule.filter(day =>
                Object.values(day).every(value => value !== '')
            )
        })
    }).then(response => response.json())
        .then(data => {
            window.location.href='/'
            return <></>
        })
        .catch(error => {
            console.error('Ocorreu um erro:', error);
        });

}