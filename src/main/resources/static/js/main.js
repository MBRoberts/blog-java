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

    // Window Scroll
    var windowScroll = function() {
        var lastScrollTop = 0;

        $(window).scroll(function(event){

            var header = $('#fh5co-header'),
                scrlTop = $(this).scrollTop;

            if ( scrlTop > 500 && scrlTop <= 2000 ) {
                header.addClass('navbar-fixed-top fh5co-animated slideInDown');
            } else if ( scrlTop <= 500) {
                if ( header.hasClass('navbar-fixed-top') ) {
                    header.addClass('navbar-fixed-top fh5co-animated slideOutUp');
                    setTimeout(function(){
                        header.removeClass('navbar-fixed-top fh5co-animated slideInDown slideOutUp');
                    }, 100 );
                }
            }

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

    var timeFormatting = function () {
        var dateElements = document.getElementsByClassName('date');
        var formattedTime = "Crap";

        for (var i = 0; i < dateElements.length; i++){

            formattedTime = moment(dateElements[i].innerText, 'yyyy-MM-DD hh:mm:ss').fromNow();

            dateElements[i].innerText = formattedTime;
        }
    };



    $(function(){
        // var $section = $('section[data-section]');

        timeFormatting();
        burgerMenu();
        navigationSection();
        windowScroll();
        // navActive();
        paginationBtns();

    });

}());

