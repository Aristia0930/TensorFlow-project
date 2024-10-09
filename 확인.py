import tensorflow as tf
from tensorflow.keras.models import load_model
# print("Num GPUs Available: ", len(tf.config.list_physical_devices('GPU')))
#  tf.saved_model.load
model = load_model("C:/Users/kjk98/OneDrive/바탕 화면/TensorFlowproject/img_model")

# 모델 서명 확인
# print(model.signatures)

# 입력과 출력 텐서 이름 확인
# for tensor in model.signatures["serving_default"].inputs:
#     print(tensor.name)

# for tensor in model.signatures["serving_default"].outputs:
#     print(tensor.name)
# 모든 operation 이름 출력
# for op in model.signatures['serving_default'].graph.get_operations():
#     print(op.name)

model.summary()  # 레이어 이름 확인