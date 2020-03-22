</section>

</div>

<div class="progress">
    <div class="line"><div class="blueline"></div></div>
</div>

<div class="tooltipContent">
    <div class="text"></div>
    <div class="close"></div>
</div>

<?php if( $_SERVER['REQUEST_URI'] == "/" ): ?>
<script src="https://cdn.jsdelivr.net/vue/latest/vue.min.js"></script>
<script src="/assets/botui/build/botui_patched.js"></script> <!-- patched version!! -->
<?php endif; ?>

<script src="/assets/jquery-3.4.1.min.js"></script>
<script src="/assets/main.js"></script>

<?php if( $_SERVER['REQUEST_URI'] == "/" ): ?>
<script src="/assets/conversation.js"></script>
<?php endif; ?>

</body>

</html>