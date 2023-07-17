import {convertMinutesToHHMMSS} from "../Utils/formater";
import Cookies from 'js-cookie';

export namespace CompanyServices{

        export function CreateCompany(selectedValue,companyData,employeeEmails,schedule){
                const token =  Cookies.get('name');
                fetch(`/api/company?duration=${convertMinutesToHHMMSS(selectedValue)}`,{
                        method: 'POST',
                        headers: {
                                'Content-Type': 'application/json',
                                'Authorization': `Bearer ${token} `
                        },
                        body: JSON.stringify({
                                company: companyData,
                                emails : {
                                        emails: employeeEmails
                                },
                                days: schedule.filter(day =>
                                    Object.values(day).every(value => value !== '')
                                )
                        })
                }).then(response =>{
                                if(response.status == 400){
                                        window.alert("Something went wrong")
                                }else if(response.status == 201){
                                        window.alert("Company created!")
                                        window.location.href = '/'
                                }

                        }
                )
        }
}