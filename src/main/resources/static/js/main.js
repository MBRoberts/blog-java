/**
 * Created by M.Ben_Roberts on 1/4/17.
 */
;(function () {

    'use strict';

    // Burger Menu
    var burgerMenu = function () {

        $('body').on('click', '.js-fh5co-nav-toggle', function (event) {

            event.preventDefault();

            if ($('#navbar').is(':visible')) {
                $(this).removeClass('active');
            } else {
                $(this).addClass('active');
            }


        });

    };

    var navActive = function(section) {

        var $el = $('#navbar > ul');
        $el.find('li').removeClass('active');
        $el.each(function(){
            $(this).find('a[data-nav-section="'+section+'"]').closest('li').addClass('active');
        });

    };

    var navigationSection = function() {

        var $section = $('section[data-section]');

        $section.waypoint(function(direction) {

            if (direction === 'down') {
                navActive($(this.element).data('section'));
            }
        }, {
            offset: '150px'
        });

        $section.waypoint(function(direction) {
            if (direction === 'up') {
                navActive($(this.element).data('section'));
            }
        }, {
            offset: function() { return -$(this.element).height() + 155; }
        });

    };


    var paginationBtns = function(){
        var currentUrl = window.location.pathname;
        var btns = document.getElementsByClassName("pageBtns");

        for (var i = 0; i < btns.length; i++){
            var btnHref = btns[i].getAttribute("href");

            if (btnHref == currentUrl){
                btns[i].classList.add("active");
            }
        }
    };

    var dateFormat = function () {
        var dateElements = document.getElementsByClassName('date');
        var formattedTime = "Crap";

        for (var i = 0; i < dateElements.length; i++){

            formattedTime = moment(dateElements[i].innerText, 'yyyy-M-D hh:mm:ss').fromNow();

            dateElements[i].innerText = formattedTime;
        }
    };

    var logoutSubmit = function() {
        var logoutBtn = document.getElementById('logout-btn');
        var logoutForm = document.getElementById('logout');

        if (logoutBtn != null) {
            logoutBtn.addEventListener('click', function () {
                logoutForm.submit();
            });
        }
    };

    var deleteSubmit = function() {
        var deleteBtn = document.getElementById('delete-btn');
        var deleteForm = document.getElementById('delete-form');

        if (deleteBtn != null) {
            deleteBtn.addEventListener('click', function () {
                deleteForm.submit();
            });
        }
    };

    $(function(){

        dateFormat();
        logoutSubmit();
        deleteSubmit();
        burgerMenu();
        navigationSection();
        paginationBtns();

    });

}());

