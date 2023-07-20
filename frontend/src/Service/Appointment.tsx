import {Fetch, SimpleFetch1} from "../Utils/useFetch";
import {Navigate} from "react-router";
import React, {useEffect, useState} from "react";
import Cookies from 'js-cookie';

export namespace Appointment{
    export function AccessDeniedPage(){
        const [location,setLocation] = useState(false)
        useEffect(() => {
            const redirectTimer = setTimeout(() => {
                window.location.href = '/'
                setLocation(true)
            }, 5000); // Redireciona para "/" após 5 segundos
            return () => clearTimeout(redirectTimer);
        }, [location]);
        return (
            <>
                <p><strong>Access denied</strong></p>
                <p>You can't access this page</p>
            </>
        );
    }

    export function addAppointmentWithManager(companyId: string,appointmentInfo:any,url:string) {
        const response = Fetch(`/company/${companyId}/appointment/employees`,'POST',appointmentInfo)
        console.log("Fetch response",response)
        if(response.response === 400) {
            window.alert(response.response)
        } else if(response.response === 403) {
            window.alert("Access denied! You can't access this page!")
        } else if(response.response === 201) {
            window.location.href = url
        } else {
            return response.response
        }
    }

    export function getUserAppointments(){
        const appointments = Fetch('/user/appointments','GET')
        if(appointments.response === 400) {
            window.alert(appointments.response)
        } else if(appointments.response === 403) {
            window.alert("Access denied! You can't access this page!")
        } else {
            return appointments.response
        }
    }

    export function addAppointment(id,obj){
        const token =  Cookies.get('name');
        fetch(`/api/company/${id}/appointment`, {
            method: 'POST',
            body: JSON.stringify(obj),
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${token}`
            }
        })
            .then(response => {
                if(response.status === 201){
                    alert("your appointment was added")
                    window.location.href = `/company/${id}`
                }else if(response.status === 400){
                    alert("Try again!Something went wrong")
                }else if(response.status === 401){
                    alert("Unauthorized")
                }
            })
            .catch(error => {
                console.error('Ocorreu um erro:', error);
            });
    }

}