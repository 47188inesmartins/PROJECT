import React, {useEffect, useState} from 'react';
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";
import '../Style/Schedule.css'

const TimePickerComponent = () => {
    const [startDate, setStartDate] = useState(new Date());

    const handleClick = (value) => {
        console.log(value)
    }


    return (
        <div className="container-fluid px-0 px-sm-4 mx-auto">
            <div className="row justify-content-center mx-0">
                <div className="col-lg-10">
                    <div className="card border-0">
                        <form autoComplete="off">
                            <div className="card-header bg-dark">
                                <div className="mx-0 mb-0 row justify-content-sm-center justify-content-start px-1">
                                    <DatePicker
                                        selected={startDate}
                                        minDate={startDate} // Define a data mínima como hoje
                                        onChange={() => {}}
                                    />
                                </div>
                            </div>
                            <div className="card-body p-3 p-sm-5">
                                <div className="row text-center mx-0">
                                    <div className="col-md-2 col-4 my-1 px-2">
                                        <div className="cell py-1">9:00AM</div>
                                    </div>
                                    <div className="col-md-2 col-4 my-1 px-2">
                                        <div className="cell py-1">9:30AM</div>
                                    </div>
                                    <div className="col-md-2 col-4 my-1 px-2">
                                        <div className="cell py-1">9:45AM</div>
                                    </div>
                                    <div className="col-md-2 col-4 my-1 px-2">
                                        <div className="cell py-1">10:00AM</div>
                                    </div>
                                    <div className="col-md-2 col-4 my-1 px-2">
                                        <div className="cell py-1" onClick={() => handleClick('10:30AM')}>10:30AM</div>
                                    </div>
                                    <div className="col-md-2 col-4 my-1 px-2">
                                        <div className="cell py-1">10:45AM</div>
                                    </div>
                                </div>
                                <div className="row text-center mx-0">
                                    <div className="col-md-2 col-4 my-1 px-2">
                                        <div className="cell py-1">11:00AM</div>
                                    </div>
                                    <div className="col-md-2 col-4 my-1 px-2">
                                        <div className="cell py-1">11:30AM</div>
                                    </div>
                                    <div className="col-md-2 col-4 my-1 px-2">
                                        <div className="cell py-1">11:45AM</div>
                                    </div>
                                    <div className="col-md-2 col-4 my-1 px-2">
                                        <div className="cell py-1">12:00PM</div>
                                    </div>
                                    <div className="col-md-2 col-4 my-1 px-2">
                                        <div className="cell py-1">12:30PM</div>
                                    </div>
                                    <div className="col-md-2 col-4 my-1 px-2">
                                        <div className="cell py-1">12:45PM</div>
                                    </div>
                                </div>
                                <div className="row text-center mx-0">
                                    <div className="col-md-2 col-4 my-1 px-2">
                                        <div className="cell py-1">1:00PM</div>
                                    </div>
                                    <div className="col-md-2 col-4 my-1 px-2">
                                        <div className="cell py-1">1:30PM</div>
                                    </div>
                                    <div className="col-md-2 col-4 my-1 px-2">
                                        <div className="cell py-1">1:45PM</div>
                                    </div>
                                    <div className="col-md-2 col-4 my-1 px-2">
                                        <div className="cell py-1">2:00PM</div>
                                    </div>
                                    <div className="col-md-2 col-4 my-1 px-2">
                                        <div className="cell py-1">2:30PM</div>
                                    </div>
                                    <div className="col-md-2 col-4 my-1 px-2">
                                        <div className="cell py-1">2:45PM</div>
                                    </div>
                                </div>
                                <div className="row text-center mx-0">
                                    <div className="col-md-2 col-4 my-1 px-2">
                                        <div className="cell py-1">3:00PM</div>
                                    </div>
                                    <div className="col-md-2 col-4 my-1 px-2">
                                        <div className="cell py-1">3:30PM</div>
                                    </div>
                                    <div className="col-md-2 col-4 my-1 px-2">
                                        <div className="cell py-1">4:15PM</div>
                                    </div>
                                    <div className="col-md-2 col-4 my-1 px-2">
                                        <div className="cell py-1">5:00PM</div>
                                    </div>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default TimePickerComponent;