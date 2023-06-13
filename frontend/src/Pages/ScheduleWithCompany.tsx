import React, {useEffect, useState} from 'react';
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";
import '../Style/Schedule.css'
import {Fetch} from "../Utils/useFetch";
import {useParams} from "react-router-dom";

function TimePickerComponent (){

    const [startDate, setStartDate] = useState(new Date());

    const params = useParams()
    const id = params.id


    const handleClick = (value) => {
        console.log(value)
    }

    function getWeekDay(date: Date): string {
        const daysOfWeek = ['SUN', 'MON', 'TUE', 'WED', 'THU', 'FRI', 'SAT'];
        const dayIndex = date.getDay();
        return daysOfWeek[dayIndex];
    }

    const myDate = startDate;
    const weekDay = getWeekDay(myDate);


    function handleDateChange(date){
        setStartDate(date)
    }


    const schedule = Fetch(`company/${id}/day/week-day?day=${weekDay}`, "GET")

    console.log("schedule = ",schedule)

    return (
        <>
            {!schedule.response?
                <div> ..loading.. </div>
                :
                <div className="container-fluid px-0 px-sm-4 mx-auto">
                    <div className="row justify-content-center mx-0">
                        <div className="col-lg-10">
                            <div className="card border-0">
                                <form autoComplete="off">
                                    <div className="card-header bg-dark">
                                        <div className="mx-0 mb-0 row justify-content-sm-center justify-content-start px-1">
                                            <DatePicker
                                                selected={startDate}
                                                onChange={(date) => handleDateChange(date)} // Atualiza o estado com a data selecionada
                                            />
                                        </div>
                                    </div>
                                    <div className="card-body p-3 p-sm-5">
                                        <div className="row text-center mx-0">
                                            {schedule.response.map((group, rowIndex) => (
                                                <div className="row text-center mx-0" key={rowIndex}>
                                                        <div className="col-md-2 col-4 my-1 px-2" key={rowIndex}>
                                                            <div className="cell py-1">{group}</div>
                                                        </div>
                                                </div>
                                            ))}
                                        </div>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            }
        </>
    )
}

export default TimePickerComponent;