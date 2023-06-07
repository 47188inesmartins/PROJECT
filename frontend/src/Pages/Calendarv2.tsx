import * as React from 'react';

const CalendarComponent = () => {
    return (
        <div className="wrapper">
            <main>
                <div className="toolbar">
                    <div className="toggle">
                        <div className="toggle__option">week</div>
                        <div className="toggle__option toggle__option--selected">month</div>
                    </div>
                    <div className="current-month">June 2016</div>
                    <div className="search-input">
                        <input type="text" value="What are you looking for?" />
                        <i className="fa fa-search"></i>
                    </div>
                </div>
                <div className="calendar">
                    <div className="calendar__header">
                        <div>mon</div>
                        <div>tue</div>
                        <div>wed</div>
                        <div>thu</div>
                        <div>fri</div>
                        <div>sat</div>
                        <div>sun</div>
                    </div>
                    <div className="calendar__week">
                        <div className="calendar__day day">1</div>
                        <div className="calendar__day day">2</div>
                        <div className="calendar__day day">3</div>
                        <div className="calendar__day day">4</div>
                        <div className="calendar__day day">5</div>
                        <div className="calendar__day day">6</div>
                        <div className="calendar__day day">7</div>
                    </div>
                    <div className="calendar__week">
                        <div className="calendar__day day">8</div>
                        <div className="calendar__day day">9</div>
                        <div className="calendar__day day">10</div>
                        <div className="calendar__day day">11</div>
                        <div className="calendar__day day">12</div>
                        <div className="calendar__day day">13</div>
                        <div className="calendar__day day">14</div>
                    </div>
                    <div className="calendar__week">
                        <div className="calendar__day day">15</div>
                        <div className="calendar__day day">16</div>
                        <div className="calendar__day day">17</div>
                        <div className="calendar__day day">18</div>
                        <div className="calendar__day day">19</div>
                        <div className="calendar__day day">20</div>
                        <div className="calendar__day day">21</div>
                    </div>
                    <div className="calendar__week">
                        <div className="calendar__day day">22</div>
                        <div className="calendar__day day">23</div>
                        <div className="calendar__day day">24</div>
                        <div className="calendar__day day">25</div>
                        <div className="calendar__day day">26</div>
                        <div className="calendar__day day">27</div>
                        <div className="calendar__day day">28</div>
                    </div>
                    <div className="calendar__week">
                        <div className="calendar__day day">29</div>
                        <div className="calendar__day day">30</div>
                        <div className="calendar__day day">31</div>
                        <div className="calendar__day day">1</div>
                        <div className="calendar__day day">2</div>
                        <div className="calendar__day day">3</div>
                        <div className="calendar__day day">4</div>
                    </div>
                </div>
            </main>
                <div className="logo">logo</div>
                <div className="avatar">
                    <div className="avatar__img">
                        <img src="https://picsum.photos/70" alt="avatar" />
                    </div>
                    <div className="avatar__name">John Smith</div>
                </div>
        </div>
    );
};

export default CalendarComponent;