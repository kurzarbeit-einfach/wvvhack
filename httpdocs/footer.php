</section>

</div>

<div class="progress">
    <div class="line"><div class="blueline"></div></div>
</div>

<div class="tooltipContent">
    <div class="text"></div>
    <div class="close"></div>
</div>

<?php if( strtok($_SERVER["REQUEST_URI"], '?') == "/" ): ?>
<script src="/assets/vuejs/vue.min.js"></script>
<script src="/assets/botui/build/vue-select.js"></script>
<script src="/assets/botui/build/botui_patched.js"></script> <!-- patched version!! -->
<script src="/assets/conversation.js?v=20200330a"></script>
<?php endif; ?>

<script src="/assets/jquery-3.4.1.min.js"></script>
<link rel="stylesheet" href="/assets/fancybox/jquery.fancybox.min.css" />
<link rel="stylesheet" href="/assets/font-awesome/css/all.css" />
<script src="/assets/fancybox/jquery.fancybox.min.js"></script>

<script src="/assets/main.js"></script>

</body>

</html>